package com.example.tabelogpage.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogpage.entity.User;
import com.example.tabelogpage.event.PasswordResetEventPublisher;
import com.example.tabelogpage.form.PasswordEditForm;
import com.example.tabelogpage.form.PasswordResetForm;
import com.example.tabelogpage.repository.UserRepository;
import com.example.tabelogpage.service.PasswordResetService;
import com.example.tabelogpage.service.PasswordResetTokenService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/passwordreset")
public class PasswordResetController {
    private final PasswordResetEventPublisher passwordResetEventPublisher;
    private final PasswordResetTokenService passwordResetTokenService;
    private final UserRepository userRepository;
    private final PasswordResetService passwordResetService;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetController(
            PasswordResetEventPublisher passwordResetEventPublisher,
            PasswordResetTokenService passwordResetTokenService,
            UserRepository userRepository,
            PasswordResetService passwordResetService,
            PasswordEncoder passwordEncoder) {
        this.passwordResetEventPublisher = passwordResetEventPublisher;
        this.passwordResetTokenService = passwordResetTokenService;
        this.userRepository = userRepository;
        this.passwordResetService = passwordResetService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String passwordreset(Model model) {
        if (!model.containsAttribute("passwordResetForm")) {
            model.addAttribute("passwordResetForm", new PasswordResetForm());
        }
        return "auth/password_reset"; 
    }

    @PostMapping
    public String postMail(
            @Valid PasswordResetForm passwordResetForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpServletRequest httpServletRequest) {

        if (bindingResult.hasErrors()) {
            return "auth/password_reset"; 
        }

        String email = passwordResetForm.getEmail();
        User user = userRepository.findByEmail(email);

        if (user != null) {
            String requestUrl = httpServletRequest.getRequestURL().toString();
            passwordResetEventPublisher.publishPasswordResetEvent(user, requestUrl, email);
            redirectAttributes.addFlashAttribute("successMessage",
                "ご入力いただいたメールアドレスにパスワード再設定用URLを送信しました。メールに記載されているリンクをクリックし、パスワードを再設定してください");
        } else {
            redirectAttributes.addFlashAttribute("passwordResetForm", passwordResetForm);
            redirectAttributes.addFlashAttribute("errorMessage",
                "入力されたメールアドレスに関連付けられたアカウントが見つかりませんでした。");
        }

        return "redirect:/passwordreset";
    }

    @GetMapping("/edit")
    public String edit(
            @RequestParam("token") String token,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (passwordResetTokenService.getPasswordResetToken(token) == null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "パスワード再設定用のURLが無効または期限切れです。再度手続きを行ってください。");
            return "redirect:/passwordreset";
        }

        if (!model.containsAttribute("passwordEditForm")) {
            PasswordEditForm passwordEditForm = new PasswordEditForm();
            passwordEditForm.setToken(token);
            model.addAttribute("passwordEditForm", passwordEditForm);
        }

        return "auth/password_edit"; 
    }

    @PostMapping("/update")
    public String updatePassword(
            @Valid PasswordEditForm passwordEditForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        User user = passwordResetService.getUserByPasswordResetToken(passwordEditForm.getToken());
        if (user == null) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "パスワード再設定用のURLが無効または期限切れです。再度手続きを行ってください。");
            return "redirect:/passwordreset";
        }

        // =======================================================
        // ★★★ ここから手動バリデーションロジックを追加 ★★★
        // =======================================================
        
        // 基本エラーがない、かつパスワードが入力されている場合に一致チェックを行う
        if (!bindingResult.hasErrors()) {
            String password = passwordEditForm.getPassword();
            String passwordConfirmation = passwordEditForm.getPasswordConfirmation();
            
            // パスワードが一致しない場合にエラーを追加
            if (!password.equals(passwordConfirmation)) {
                // エラーメッセージのキーと、エラーを紐づけるフィールドを指定
                bindingResult.rejectValue("passwordConfirmation", "error.passwordEditForm", "パスワードが一致しません。");
            }
        }
        
        // =======================================================
        // ★★★ 追加したバリデーションも含めてエラーをチェック ★★★
        // =======================================================
        if (bindingResult.hasErrors()) {
            // エラーがあった場合は、編集画面に戻る
            return "auth/password_edit"; 
        }

        passwordResetService.updatePassword(passwordEditForm, passwordEncoder, user);
        passwordResetTokenService.deleteToken(passwordEditForm.getToken()); 
        
        // ★★★ デバッグログを追加 ★★★
        System.out.println("DEBUG: パスワード更新成功。Flash Attributeをセットして/loginへリダイレクトします。");

        // ★★★ Flash Attribute を使用してリダイレクトします ★★★
        redirectAttributes.addFlashAttribute("successMessage", 
            "パスワードを再設定しました。新しいパスワードでログインしてください。");
            
        // クエリパラメータなしの/loginにリダイレクト
        return "redirect:/login";
    }
}