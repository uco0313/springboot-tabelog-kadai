package com.example.tabelogpage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                .requestMatchers(
                    "/css/**", "/images/**", "/js/**", "/storage/**", // 静的リソース
                    "/", "/signup/**", "/stores", "/stores/", "/stores/{id}", "/stores/{storeId}/reviews","/company", 
                    // パスワードリセット関連の全てのURLを許可
                    "/passwordreset", "/passwordreset/**", "/login"
                ).permitAll()
                
                // 管理者にのみアクセスを許可するURL
                .requestMatchers("/admin/**", "/admin/stores/export/csv").hasRole("ADMIN")
                
                // 上記以外のURLはログインが必要（会員または管理者のどちらでもOK）
                .anyRequest().authenticated()
            )
            
            // セッション管理の設定を追加し、リダイレクト時のURLパラメータ消失を防ぎます
            .sessionManagement((session) -> session
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            )
            
            .formLogin((form) -> form
                .loginPage("/login")      // ログインページのURL
                .loginProcessingUrl("/login")    // ログインフォームの送信先URL
                .defaultSuccessUrl("/?loggedIn") // ログイン成功時のリダイレクト先URL
                .failureUrl("/login?error")      // ログイン失敗時のリダイレクト先URL
                .permitAll()
            )
            .logout((logout) -> logout
                .logoutSuccessUrl("/?loggedOut") // ログアウト時のリダイレクト先URL
                .permitAll()
            );

        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}