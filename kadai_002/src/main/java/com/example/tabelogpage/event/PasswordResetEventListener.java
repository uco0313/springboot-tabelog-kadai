package com.example.tabelogpage.event;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value; // ★追加
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.tabelogpage.entity.User;
import com.example.tabelogpage.service.PasswordResetTokenService;

@Component
public class PasswordResetEventListener {
    private final PasswordResetTokenService passwordResetTokenService;
    private final JavaMailSender javaMailSender;
    private final String fromAddress; // ★追加: Fromアドレスを保持するフィールド

    public PasswordResetEventListener(
            PasswordResetTokenService passwordResetTokenService,
            JavaMailSender javaMailSender,
            @Value("${spring.mail.properties.mail.smtp.from}") String fromAddress // ★@Valueでプロパティをインジェクション
    ) {
        this.passwordResetTokenService = passwordResetTokenService;
        this.javaMailSender = javaMailSender;
        this.fromAddress = fromAddress; // ★フィールドに設定
    }

    // PasswordResetEventを受け取ったときに実行されるメソッド
    @EventListener
    public void onApplicationEvent(PasswordResetEvent passwordResetEvent) {
        this.confirmPasswordReset(passwordResetEvent);
    }

    private void confirmPasswordReset(PasswordResetEvent passwordResetEvent) {
        User user = passwordResetEvent.getUser();
        String requestEmail = passwordResetEvent.getRequestEmail();
        
        // 1. トークンの生成と保存
        String token = UUID.randomUUID().toString();
        passwordResetTokenService.create(user, token); // DBにトークンを保存

        // 2. メールメッセージの作成
        String recipientAddress = requestEmail;
        String subject = "パスワード再設定のご案内";
        // URLの組み立て: http://localhost:8080/passwordreset/edit?token=...
        String confirmationUrl = passwordResetEvent.getRequestUrl().replace("/passwordreset", "/passwordreset/edit?token=") + token;
        
        String message = "以下のリンクをクリックして、新しいパスワードを設定してください。\n\n" + confirmationUrl + "\n\nこのリンクは1時間で失効します。";

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setFrom(this.fromAddress); // ★修正: 送信元アドレスを設定
        email.setSubject(subject);
        email.setText(message);
        
        // 3. メールの送信 (SMTP設定が必須)
        javaMailSender.send(email);
    }
}