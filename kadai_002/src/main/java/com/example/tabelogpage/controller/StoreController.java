package com.example.tabelogpage.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogpage.entity.Favorite;
import com.example.tabelogpage.entity.Review;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.entity.User;
import com.example.tabelogpage.form.ReservationInputForm;
import com.example.tabelogpage.security.UserDetailsImpl;
import com.example.tabelogpage.service.FavoriteService;
import com.example.tabelogpage.service.ReviewService;
import com.example.tabelogpage.service.StoreService;

@Controller
@RequestMapping("/stores")
public class StoreController {
    private final StoreService storeService;
    private final ReviewService reviewService;
    private final FavoriteService favoriteService;  
    
   
    public StoreController(StoreService storeService ,ReviewService reviewService,FavoriteService favoriteService) {
        this.storeService = storeService;
        this.reviewService = reviewService;
        this.favoriteService = favoriteService;
    }
    
    @GetMapping
    public String index(Model model, 
                        @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable,
                        @RequestParam(name = "keyword", required = false) String keyword,
                        @RequestParam(name = "area", required = false) String area,
                        @RequestParam(name = "priceMin", required = false) Integer priceMin,
                        @RequestParam(name = "priceMax", required = false) Integer priceMax,
                        @RequestParam(name = "categoryId", required = false) Integer categoryId,
                        @RequestParam(name = "order", required = false) String order) {
        
        Page<Store> storePage;
        
        // --- 1. 検索条件の判定と準備 ---
        
        // ソート条件の調整
        if (order != null && order.equals("priceAsc")) {
            // 予算が低い順にソート（昇順）：priceMinを基準
        	
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.ASC, "priceMin");
        } else if (order != null && order.equals("priceDesc")) {
        	
            // 予算が高い順にソート（降順）：priceMaxを基準
            pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Direction.DESC, "priceMax");
        }
        
        // 検索条件フラグ (キーワードリセットのために使用)
        boolean hasKeyword = (keyword != null && !keyword.isEmpty());
        
        
        // --- 2. 複合検索ロジック (Service層へ委譲) ---
        
        // ★修正: すべての検索ロジックをStoreServiceのfindByCriteriaメソッドに委譲
        storePage = storeService.findByCriteria(keyword, area, priceMin, priceMax, categoryId, pageable);
        
        if (hasKeyword) {
            // キーワード検索時は他の検索パラメータをリセット（ビューに渡す値のため、Controllerに残す）
            categoryId = null;
            area = null;
            priceMin = null;
            priceMax = null;
        }
        
        
        // --- 3. Modelへの追加 ---
        
        model.addAttribute("storePage", storePage);
        
        //Serviceのメソッドを呼び出し、カテゴリリストを取得
        model.addAttribute("categoryList", storeService.findAllCategories());
        
        // 検索条件をビューに渡す 
        model.addAttribute("keyword", keyword);
        model.addAttribute("area", area);
        model.addAttribute("priceMin", priceMin);
        model.addAttribute("priceMax", priceMax);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("order", order);
        
        return "stores/index";
    }
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
            RedirectAttributes redirectAttributes,
            Model model) 
{
    	// findStoreByIdは元々Service経由のため変更なし
    	Optional<Store> optionalStore  = storeService.findStoreById(id);

        if (optionalStore.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");

            return "redirect:/stores";
        }

        Store store = optionalStore.get();        
        boolean hasUserAlreadyReviewed = false; 
        Favorite favorite = null;        
        boolean isFavorite = false;
        
        if (userDetailsImpl != null) {
            User user = userDetailsImpl.getUser();
            hasUserAlreadyReviewed = reviewService.hasUserAlreadyReviewed(store, user);  
            isFavorite = favoriteService.isFavorite(store, user);
            
            if (isFavorite) {
                favorite = favoriteService.findFavoriteByStoreAndUser(store, user);
            }  
        }  
        
        List<Review> newReviews = reviewService.findTop6ReviewsByStoreOrderByCreatedAtDesc(store);        
        long totalReviewCount = reviewService.countReviewsByStore(store);       

        
        model.addAttribute("store", store);         
        model.addAttribute("reservationInputForm", new ReservationInputForm());
        model.addAttribute("hasUserAlreadyReviewed", hasUserAlreadyReviewed);
        model.addAttribute("newReviews", newReviews);        
        model.addAttribute("totalReviewCount", totalReviewCount);   
        model.addAttribute("favorite", favorite);
        model.addAttribute("isFavorite", isFavorite);
        
        return "stores/show";
    }    
}