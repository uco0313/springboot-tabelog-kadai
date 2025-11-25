package com.example.tabelogpage.controller;

import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.form.StoreEditForm;
import com.example.tabelogpage.form.StoreRegisterForm;
import com.example.tabelogpage.repository.CategoryRepository;
import com.example.tabelogpage.repository.StoreRepository;
import com.example.tabelogpage.service.StoreService;

@Controller
@RequestMapping("/admin/stores") 
public class AdminStoreController {
    
    
    private final StoreRepository storeRepository;
    private final StoreService storeService;    
    private final CategoryRepository categoryRepository; 
    

    public AdminStoreController(StoreRepository storeRepository, StoreService storeService, CategoryRepository categoryRepository)  {
        this.storeRepository = storeRepository;
        this.storeService = storeService;  
        this.categoryRepository = categoryRepository; 
    }
    
    @GetMapping 
    public String index(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, @RequestParam(name = "keyword", required = false) String keyword){
        
        Page<Store> storePage;
        
        if (keyword != null && !keyword.isEmpty()) {
            storePage = storeRepository.findByStoreNameLike("%" + keyword + "%", pageable);
        } else {
            storePage = storeRepository.findAll(pageable);
        }
        model.addAttribute("storePage", storePage);
        model.addAttribute("keyword", keyword);
        
        return "admin/stores/index";
    } 
    


    
    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("storeRegisterForm", new StoreRegisterForm());
        model.addAttribute("categoryList", categoryRepository.findAll()); 
        return "admin/stores/register";
    } 
    
    
    @PostMapping("/create")
    public String create(@ModelAttribute @Validated StoreRegisterForm storeRegisterForm, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("categoryList", categoryRepository.findAll());
            return "admin/stores/register";
        }
       
      // 入力チェック
       if(storeRegisterForm.getPriceMin() > storeRegisterForm.getPriceMax()) {
    	    model.addAttribute("categoryList", categoryRepository.findAll());
    	    model.addAttribute("errorMessage", "価格を適正に入力してください。");
    	    return "admin/stores/register";
    	}
       
        storeService.create(storeRegisterForm);
        redirectAttributes.addFlashAttribute("successMessage", "店舗を登録しました。");    
        
        return "redirect:/admin/stores";
    }  
    
    @GetMapping("/{id}")
    public String show(@PathVariable(name = "id") Integer id, Model model) {
        Store store = storeRepository.getReferenceById(id);
        
        model.addAttribute("store", store);
        
        return "admin/stores/show";
    } 
    
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        
        Store store = storeRepository.getReferenceById(id);
        
        String imagePath = store.getImagePath(); 
        
        // StoreEditFormの定義に合わせて13個の引数を正しい順番で渡す
        StoreEditForm storeEditForm = new StoreEditForm(
            store.getId(),                                  // 1. id
            store.getStoreName(),                           // 2. name
            store.getCategory().getId(),                    // 3. categoryId
            null,                                           // 4. imageFile
            store.getDescription(),                         // 5. description
            store.getPriceMin(),                            // 6. priceMin
            store.getPriceMax(),                            // 7. priceMax
            store.getOpeningTime().format(timeFormatter),   // 8. openingTime (LocalTimeをStringに変換)
            store.getClosingTime().format(timeFormatter),   // 9. closingTime (LocalTimeをStringに変換)
            store.getPostalCode(),                          // 10. postalCode
            store.getAddress(),                             // 11. address
            store.getPhoneNumber(),                         // 12. phoneNumber
            store.getRegularHoliday()                       // 13. regularHoliday
        );
        
        model.addAttribute("imagePath", imagePath);        
        model.addAttribute("storeEditForm", storeEditForm); 
        model.addAttribute("categoryList", categoryRepository.findAll()); 
        
        return "admin/stores/edit";                      
    }
    
    @PostMapping("/{id}/update")
    public String update(
        @PathVariable(name = "id") Integer id, 
        @ModelAttribute @Validated StoreEditForm storeEditForm, 
        BindingResult bindingResult, 
        RedirectAttributes redirectAttributes, 
        Model model  ) {        
      
        if (bindingResult.hasErrors()) {
            // エラー発生時、edit.htmlに戻るために必要なデータをModelに再セット
            Store store = storeRepository.getReferenceById(id);
            model.addAttribute("imagePath", store.getImagePath());
            model.addAttribute("categoryList", categoryRepository.findAll());
            
            return "admin/stores/edit";
        }
      
        storeService.update(storeEditForm);
        redirectAttributes.addFlashAttribute("successMessage", "店舗情報を編集しました。");
        
        return "redirect:/admin/stores";
         }    
    
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {        
        storeRepository.deleteById(id);
                
        redirectAttributes.addFlashAttribute("successMessage", "店舗を削除しました。");
        
        return "redirect:/admin/stores";
    }    
}