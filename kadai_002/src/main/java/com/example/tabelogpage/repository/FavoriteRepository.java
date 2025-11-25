package com.example.tabelogpage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogpage.entity.Favorite;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.entity.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer> {
   public Page<Favorite> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
   public Favorite findByStoreAndUser(Store store, User user);
}