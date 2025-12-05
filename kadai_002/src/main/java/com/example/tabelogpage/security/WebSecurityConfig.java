package com.example.tabelogpage.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    // Spring Securityが自動的にUserDetailsServiceImplをBeanから取得するためです。

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // すべてのユーザーにアクセスを許可するURLを統合して記述
                .requestMatchers(
                    "/css/**", "/images/**", "/js/**", "/storage/**", 
                    "/", "/signup/**", "/stores", "/stores/", "/stores/{id}", "/stores/{storeId}/reviews","/company","/index", 
                    "/passwordreset", "/passwordreset/**", "/login","/categories", "/search"
                ).permitAll()
                
                // 無料会員のみアクセスを許可するURL
                .requestMatchers("/subscription/register", "/subscription/create").hasRole("FREE")
                
                // 有料会員にのみアクセスを許可するURL
                .requestMatchers(
                	    "/reservations/confirm", "/reservations/index", "/reservations/**", "/favorites/**",
                	    "/reviews/edit", "/reviews/register", "/subscription/edit", "/subscription/update", "/subscription/cancel", "/subscription/delete"
                	).hasRole("PAID")
                
                // 管理者にのみアクセスを許可するURL
                .requestMatchers("/admin/**", "/admin/stores/export/csv").hasRole("ADMIN")
                
                // 上記以外のURLはログインが必要
                .anyRequest().authenticated()
            )
            
            .formLogin((form) -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/?loggedIn")
                .failureUrl("/login?error")
                .permitAll()
            )
            
            .logout((logout) -> logout
                .logoutSuccessUrl("/?loggedOut")
                .permitAll()
            )
            
            // CSRF除外設定を最後に記述
            .csrf((csrf) -> csrf.ignoringRequestMatchers("/stripe/webhook"));
            		
            
        return http.build();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }    
}