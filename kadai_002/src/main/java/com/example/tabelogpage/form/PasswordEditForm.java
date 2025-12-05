package com.example.tabelogpage.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordEditForm {

    // パスワードリセットトークン（hiddenフィールドなどで受け取る）
    private String token;

    @NotBlank(message = "新しいパスワードを入力してください。")
    @Size(min = 8, message = "パスワードは8文字以上で入力してください。")
    private String password;

    @NotBlank(message = "確認用のパスワードを入力してください。")
    private String passwordConfirmation;
    
}