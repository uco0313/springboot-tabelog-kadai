package com.example.tabelogpage.event;

import org.springframework.context.ApplicationEvent;

import com.example.tabelogpage.entity.User;

public class PasswordResetEvent extends ApplicationEvent {
    
    private final User user;
    private final String requestUrl;
    private final String requestEmail;

    /**
     * パスワードリセットイベントのコンストラクタ
     * @param source イベントを発生させたオブジェクト (通常は this を渡す)
     * @param user パスワードリセットの対象ユーザー
     * @param requestUrl リセットリンク作成のためのベースURL (例: http://localhost:8080/passwordreset)
     * @param requestEmail リセットメールの宛先メールアドレス
     */
    public PasswordResetEvent(Object source, User user, String requestUrl, String requestEmail) {
        super(source);
        this.user = user;
        this.requestUrl = requestUrl;
        this.requestEmail = requestEmail;
    }

    // --- Getter メソッド ---

    public User getUser() {
        return user;
    }

    public String getRequestUrl() {
        return requestUrl;
    }

    public String getRequestEmail() {
        return requestEmail;
    }
}