package com.example.tabelogpage.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "password_reset_tokens")
@Data
public class PasswordResetToken {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    
    // トークン文字列
    @Column(name = "token", unique = true, nullable = false)
    private String token;
    
    // トークンの有効期限
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;
    
    // トークンを発行されたユーザー
    // OneToOne: UserとPasswordResetTokenは1対1の関係
    // JoinColumn: 外部キーとして user_id を持つ
    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;
}