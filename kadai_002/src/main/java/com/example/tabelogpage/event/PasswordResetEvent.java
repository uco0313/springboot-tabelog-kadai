package com.example.tabelogpage.event;

import org.springframework.context.ApplicationEvent;

import com.example.tabelogpage.entity.User;

public class PasswordResetEvent extends ApplicationEvent {
    
    private final User user;
    private final String requestUrl;
    private final String requestEmail;


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