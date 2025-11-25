package com.example.tabelogpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tabelogpage.entity.PasswordResetToken;
import com.example.tabelogpage.entity.User; // User エンティティをインポート

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
    
    // トークン文字列に基づいてトークンを取得するメソッド
    PasswordResetToken findByToken(String token);
    
    // ユーザーIDに基づいてトークンを削除する（トークン再発行時に使用）
    @Modifying
    @Query("DELETE FROM PasswordResetToken prt WHERE prt.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);
    
    /**
     * ★追加: Userエンティティを受け取って関連するトークンを削除するメソッド
     * パスワード更新完了時に使用し、既存の有効なトークンを削除する。
     */
    @Modifying 
    void deleteByUser(User user);
}