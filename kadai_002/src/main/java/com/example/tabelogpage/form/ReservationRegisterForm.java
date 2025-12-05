package com.example.tabelogpage.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationRegisterForm {    
    
    private Integer storeId; 
        
    private Integer userId;    
    
    @NotNull(message = "予約日時を選択してください。") 
    @Size(min = 1, message = "予約日時を選択してください。")
    private String reservationDate; // flatpickrから "Y/m/d H:i" 形式で文字列として受け取る
    
    //必須チェック、1名以上チェックを追加
    @NotNull(message = "予約人数を入力してください。")
    @Min(value = 1, message = "予約人数は1名以上で入力してください。")
    private Integer numberOfPeople; 
    
}