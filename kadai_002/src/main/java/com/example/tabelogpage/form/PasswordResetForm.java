package com.example.tabelogpage.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetForm {

    @NotBlank(message = "メールアドレスを入力してください。")
    @Email(message = "メールアドレスの形式が正しくありません。")
    private String email;
}