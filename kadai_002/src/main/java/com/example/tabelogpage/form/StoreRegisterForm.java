package com.example.tabelogpage.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class StoreRegisterForm {
    
    // 店舗名
    @NotBlank(message = "店舗名を入力してください。")
    private String name;
    
    // カテゴリ
    @NotNull(message = "カテゴリを選択してください。")
    private Integer categoryId;
        
    // 画像
    private MultipartFile imageFile;
    
    // 説明
    @NotBlank(message = "説明を入力してください。")
    private String description;
    
    // 価格帯(下限)
    @NotNull(message = "価格帯(下限)を入力してください。")
    @Min(value = 0, message = "価格帯は0円以上に設定してください。")
    private Integer priceMin;   
    
    // 価格帯(上限)
    @NotNull(message = "価格帯(上限)を入力してください。")
    @Min(value = 0, message = "価格帯は0円以上に設定してください。")
    private Integer priceMax;
    
    // 営業時間(開店) 
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "開店時間をHH:MM形式で入力してください。")
    @NotNull(message = "開店時間を入力してください。") 
    private String openingTime;
    
    // 営業時間(閉店)
    @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "閉店時間をHH:MM形式で入力してください。")
    @NotNull(message = "閉店時間を入力してください。") 
    private String closingTime;
    
    // 郵便番号 
    @Pattern(regexp = "^[0-9]{3}-[0-9]{4}$", message = "郵便番号はXXX-XXXXの形式で入力してください。")
    @NotNull(message = "郵便番号を入力してください。") 
    private String postalCode;
    
    // 住所
    @NotBlank(message = "住所を入力してください。")
    private String address;
    
    // 電話番号 
    @Pattern(regexp = "^[0-9\\-]+$", message = "電話番号は半角数字とハイフンのみで入力してください。")
    @NotNull(message = "電話番号を入力してください。")
    private String phoneNumber;
    
    // 定休日
    @NotBlank(message = "定休日を入力してください。")
    private String regularHoliday;
    
}