package com.example.tabelogpage.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabelogpage.entity.Review;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.entity.User;
import com.example.tabelogpage.form.ReviewEditForm;
import com.example.tabelogpage.form.ReviewRegisterForm;
import com.example.tabelogpage.repository.ReviewRepository;

@Service
public class ReviewService {
   private final ReviewRepository reviewRepository;

   public ReviewService(ReviewRepository reviewRepository) {
       this.reviewRepository = reviewRepository;
   }

   // 指定したidを持つレビューを取得する
   public Optional<Review> findReviewById(Integer id) {
       return reviewRepository.findById(id);
   }

   // 指定した店舗のレビューを作成日時が新しい順に6件取得する
   public List<Review> findTop6ReviewsByStoreOrderByCreatedAtDesc(Store store) {
       return reviewRepository.findTop6ByStoreOrderByCreatedAtDesc(store);
   }

   // 指定した店舗とユーザーのレビューを取得する
   public Review findReviewByStoreAndUser(Store store, User user) {
       return reviewRepository.findByStoreAndUser(store, user);
   }

   // 指定した店舗のレビュー件数を取得する
   public long countReviewsByStore(Store store) {
       return reviewRepository.countByStore(store);
   }

   // 指定した店舗のすべてのレビューを作成日時が新しい順に並べ替え、ページングされた状態で取得する
   public Page<Review> findReviewsByStoreOrderByCreatedAtDesc(Store store, Pageable pageable) {
       return reviewRepository.findByStoreOrderByCreatedAtDesc(store, pageable);
   }

   @Transactional
   public void createReview(ReviewRegisterForm reviewRegisterForm, Store store, User user) {
       Review review = new Review();

       review.setStore(store);
       review.setUser(user);
       review.setStarRating(reviewRegisterForm.getStarRating());
       review.setComment(reviewRegisterForm.getComment());

       reviewRepository.save(review);
   }

   @Transactional
   public void updateReview(ReviewEditForm reviewEditForm, Review review) {
       review.setStarRating(reviewEditForm.getStarRating());
       review.setComment(reviewEditForm.getComment());

       reviewRepository.save(review);
   }

   @Transactional
   public void deleteReview(Review review) {
       reviewRepository.delete(review);
   }

   // 指定したユーザーが指定した店舗のレビューをすでに投稿済みかどうかをチェックする
   public boolean hasUserAlreadyReviewed(Store store, User user) {
       return reviewRepository.findByStoreAndUser(store, user) != null;
   }
}