package com.example.tabelogpage.controller;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogpage.entity.Review;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.entity.User;
import com.example.tabelogpage.form.ReviewEditForm;
import com.example.tabelogpage.form.ReviewRegisterForm;
import com.example.tabelogpage.security.UserDetailsImpl;
import com.example.tabelogpage.service.ReviewService;
import com.example.tabelogpage.service.StoreService;

@Controller
@RequestMapping("/stores/{storeId}/reviews")
public class ReviewController {
   private final StoreService storeService;
   private final ReviewService reviewService;

   public ReviewController(StoreService storeService, ReviewService reviewService) {
       this.storeService = storeService;
       this.reviewService = reviewService;
   }

   @GetMapping
   public String index(@PathVariable(name = "storeId") Integer storeId,
                       @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable,
                       RedirectAttributes redirectAttributes,
                       Model model)
   {
       Optional<Store> optionalStore  = storeService.findStoreById(storeId);

       if (optionalStore.isEmpty()) {
           redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");

           return "redirect:/stores";
       }

       Store store = optionalStore.get();
       Page<Review> reviewPage = reviewService.findReviewsByStoreOrderByCreatedAtDesc(store, pageable);

       model.addAttribute("store", store);
       model.addAttribute("reviewPage", reviewPage);

       return "reviews/index";
   }

   @GetMapping("/register")
   public String register(@PathVariable(name = "storeId") Integer storeId, RedirectAttributes redirectAttributes, Model model) {
       Optional<Store> optionalStore  = storeService.findStoreById(storeId);

       if (optionalStore.isEmpty()) {
           redirectAttributes.addFlashAttribute("errorMessage", "店舗が存在しません。");

           return "redirect:/stores";
       }

       Store store = optionalStore.get();

       model.addAttribute("store", store);
       model.addAttribute("reviewRegisterForm", new ReviewRegisterForm());

       return "reviews/register";
   }

   @PostMapping("/create")
   public String create(@PathVariable(name = "storeId") Integer storeId,
                        @ModelAttribute @Validated ReviewRegisterForm reviewRegisterForm,
                        BindingResult bindingResult,
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

       if (bindingResult.hasErrors()) {
           model.addAttribute("store", store);
           model.addAttribute("reviewRegisterForm", reviewRegisterForm);

           return "reviews/register";
       }

       User user = userDetailsImpl.getUser();

       reviewService.createReview(reviewRegisterForm, store, user);
       redirectAttributes.addFlashAttribute("successMessage", "レビューを投稿しました。");

       return "redirect:/stores/{storeId}";
   }

   @GetMapping("/{reviewId}/edit")
   public String edit(@PathVariable(name = "storeId") Integer storeId,
                      @PathVariable(name = "reviewId") Integer reviewId,
                      @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                      RedirectAttributes redirectAttributes,
                      Model model)
   {
       Optional<Store> optionalStore  = storeService.findStoreById(storeId);
       Optional<Review> optionalReview  = reviewService.findReviewById(reviewId);

       if (optionalStore.isEmpty() || optionalReview.isEmpty()) {
           redirectAttributes.addFlashAttribute("errorMessage", "指定されたページが見つかりません。");

           return "redirect:/stores";
       }

       Store store = optionalStore.get();
       Review review = optionalReview.get();
       User user = userDetailsImpl.getUser();

       if (!review.getStore().getId().equals(store.getId()) || !review.getUser().getId().equals(user.getId())) {
           redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです。");

           return "redirect:/stores/{storeId}";
       }

       ReviewEditForm reviewEditForm = new ReviewEditForm(review.getStarRating(), review.getComment());

       model.addAttribute("store", store);
       model.addAttribute("review", review);
       model.addAttribute("reviewEditForm", reviewEditForm);

       return "reviews/edit";
   }

   @PostMapping("/{reviewId}/update")
   public String update(@PathVariable(name = "storeId") Integer storeId,
                        @PathVariable(name = "reviewId") Integer reviewId,
                        @ModelAttribute @Validated ReviewEditForm reviewEditForm,
                        BindingResult bindingResult,
                        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                        RedirectAttributes redirectAttributes,
                        Model model)
   {
       Optional<Store> optionalStore = storeService.findStoreById(storeId);
       Optional<Review> optionalReview  = reviewService.findReviewById(reviewId);

       if (optionalStore.isEmpty() || optionalReview.isEmpty()) {
           redirectAttributes.addFlashAttribute("errorMessage", "指定されたページが見つかりません。");

           return "redirect:/stores";
       }

       Store store = optionalStore.get();
       Review review = optionalReview.get();
       User user = userDetailsImpl.getUser();

       if (!review.getStore().getId().equals(store.getId()) || !review.getUser().getId().equals(user.getId())) {
           redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです。");

           return "redirect:/stores/{storeId}";
       }

       if (bindingResult.hasErrors()) {
           model.addAttribute("store", store);
           model.addAttribute("review", review);
           model.addAttribute("reviewEditForm", reviewEditForm);

           return "reviews/edit";
       }

       reviewService.updateReview(reviewEditForm, review);
       redirectAttributes.addFlashAttribute("successMessage", "レビューを編集しました。");

       return "redirect:/stores/{storeId}";
   }

   @PostMapping("/{reviewId}/delete")
   public String delete(@PathVariable(name = "storeId") Integer storeId,
                        @PathVariable(name = "reviewId") Integer reviewId,
                        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
                        RedirectAttributes redirectAttributes)
   {
       Optional<Store> optionalStore  = storeService.findStoreById(storeId);
       Optional<Review> optionalReview  = reviewService.findReviewById(reviewId);

       if (optionalStore.isEmpty() || optionalReview.isEmpty()) {
           redirectAttributes.addFlashAttribute("errorMessage", "指定されたページが見つかりません。");

           return "redirect:/stores";
       }

       Store store = optionalStore.get();
       Review review = optionalReview.get();
       User user = userDetailsImpl.getUser();

       if (!review.getStore().getId().equals(store.getId()) || !review.getUser().getId().equals(user.getId())) {
           redirectAttributes.addFlashAttribute("errorMessage", "不正なアクセスです。");

           return "redirect:/stores/{storeId}";
       }

       reviewService.deleteReview(review);
       redirectAttributes.addFlashAttribute("successMessage", "レビューを削除しました。");

       return "redirect:/stores/{storeId}";
   }
}