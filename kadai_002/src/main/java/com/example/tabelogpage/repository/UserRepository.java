package com.example.tabelogpage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogpage.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
    

    User findByEmail(String email);
    

    User findByStripeCustomerId(String stripeCustomerId);


    Page<User> findByNameLikeOrFuriganaLikeOrEmailLike(String nameLike, String furiganaLike, String emailLike, Pageable pageable);

}