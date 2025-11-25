package com.example.tabelogpage.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.example.tabelogpage.entity.User;

@Component
public class PasswordResetEventPublisher {
    
    private final ApplicationEventPublisher applicationEventPublisher;

    public PasswordResetEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishPasswordResetEvent(User user, String requestUrl, String requestEmail) {
        // パスワードリセットイベントを作成
        PasswordResetEvent passwordResetEvent = new PasswordResetEvent(this, user, requestUrl, requestEmail);
        
        // イベントを発行
        applicationEventPublisher.publishEvent(passwordResetEvent);
    }
}