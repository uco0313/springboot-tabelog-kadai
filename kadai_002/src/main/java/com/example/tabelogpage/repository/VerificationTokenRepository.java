package com.example.tabelogpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogpage.entity.VerificationToken;


public interface VerificationTokenRepository extends JpaRepository< VerificationToken, Integer> {
    public VerificationToken findByToken(String token);
}