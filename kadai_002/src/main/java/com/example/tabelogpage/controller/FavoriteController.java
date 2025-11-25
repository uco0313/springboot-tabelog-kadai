package com.example.tabelogpage.controller;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogpage.entity.Favorite;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.entity.User;
import com.example.tabelogpage.security.UserDetailsImpl;
import com.example.tabelogpage.service.FavoriteService;
import com.example.tabelogpage.service.StoreService;

@Controller
public class FavoriteController {
   private final StoreService storeService;
   private final FavoriteService favoriteService;

   public FavoriteController(StoreService storeService, FavoriteService favoriteService) {
       this.storeService = storeService;
       this.favoriteService = favoriteService;
   }

   @GetMapping("/favorites")
   public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                       @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable,
                       Model model)
   {
       User user = userDetailsImpl.getUser();
       Page<Favorite> favoritePage = favoriteService.findFavoritesByUserOrderByCreatedAtDesc(user, pageable);

       model.addAttribute("favoritePage", favoritePage);

       return "favorites/index";
   }

   @PostMapping("/stores/{storeId}/favorites/create")
   public String create(@PathVariable(name = "storeId") Integer storeId,
                        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                        RedirectAttributes redirectAttributes,
                        Model model)
   {
       Optional<Store> optionalStore  = storeService.findStoreById(storeId);

       if (optionalStore.isEmpty()) {
           redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");

           return "redirect:/stores";
       }

       Store store = optionalStore.get();
       User user = userDetailsImpl.getUser();

       favoriteService.createFavorite(store, user);
       redirectAttributes.addFlashAttribute("successMessage", "お気に入りに追加しました。");

       return "redirect:/stores/{storeId}";
   }

   @PostMapping("/stores/{storeId}/favorites/{favoriteId}/delete")
   public String delete(@PathVariable(name = "storeId") Integer storeId,
                        @PathVariable(name = "favoriteId") Integer favoriteId,
                        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                        RedirectAttributes redirectAttributes)
   {
       Optional<Store> optionalStore  = storeService.findStoreById(storeId);
       Optional<Favorite> optionalFavorite  = favoriteService.findFavoriteById(favoriteId);

       if (optionalStore.isEmpty() || optionalFavorite.isEmpty()) {
           redirectAttributes.addFlashAttribute("errorMessage", "指定されたページが見つかりません。");

           return "redirect:/stores";
       }

       Store store = optionalStore.get();
       Favorite favorite = optionalFavorite.get();
       User user = userDetailsImpl.getUser();

       if (!favorite.getStore().getId().equals(store.getId()) || !favorite.getUser().getId().equals(user.getId())) {
           redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです。");

           return "redirect:/stores/{storeId}";
       }

       favoriteService.deleteFavorite(favorite);
       redirectAttributes.addFlashAttribute("successMessage", "お気に入りを解除しました。");

       return "redirect:/stores/{storeId}";
   }
}