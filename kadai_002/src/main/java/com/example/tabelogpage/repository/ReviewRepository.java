package com.example.tabelogpage.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogpage.entity.Review;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.entity.User;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
   public List<Review> findTop6ByStoreOrderByCreatedAtDesc(Store store);
   public Review findByStoreAndUser(Store store, User user);
   public long countByStore(Store store);
   public Page<Review> findByStoreOrderByCreatedAtDesc(Store store, Pageable pageable);
}