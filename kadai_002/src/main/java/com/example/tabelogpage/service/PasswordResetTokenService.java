package com.example.tabelogpage.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabelogpage.entity.PasswordResetToken;
import com.example.tabelogpage.entity.User;
import com.example.tabelogpage.repository.PasswordResetTokenRepository;

@Service
public class PasswordResetTokenService {
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    
    public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }
    
    // トークンを作成する
    @Transactional
    public void create(User user, String token) {
        // 新しいトークンを作成する前に、既存のトークンを削除
        deleteExistingToken(user.getId()); 
        
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        passwordResetToken.setUser(user);
        passwordResetToken.setToken(token);
        // トークン有効期限を現在時刻から1時間後に設定
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(1)); 
        passwordResetTokenRepository.save(passwordResetToken);
    }
    
    // トークン文字列に基づいてトークンを取得する
    public PasswordResetToken getPasswordResetToken(String token) {
        return passwordResetTokenRepository.findByToken(token);
    }
    
    // トークンを削除する（パスワード更新時に使用）
    @Transactional
    public void deleteToken(String token) {
        // ★修正箇所: まずトークンエンティティを取得し、nullチェックを行ってから削除を実行
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        
        if (passwordResetToken != null) {
            passwordResetTokenRepository.delete(passwordResetToken);
        }
        // null の場合は何もしない（例外発生を回避）
    }
    
    /**
     * ユーザーIDに基づいて既存のトークンを削除するメソッド
     * @param userId ユーザーID
     */
    @Transactional
    public void deleteExistingToken(Integer userId) {
        passwordResetTokenRepository.deleteByUserId(userId);
    }
}