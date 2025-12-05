package com.example.tabelogpage.form;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationInputForm {
    @NotBlank(message = "予約日時を選択してください。")
    private String reservationDate;    
    
    @NotNull(message = "予約人数を入力してください。")
    @Min(value = 1, message = "予約人数は1人以上に設定してください。")
    private Integer numberOfPeople; 

    public LocalDateTime getConvertedReservationDate() {
    	
    	// this.reservationDate が null または空文字でないかチェックする
    	if (this.reservationDate == null || this.reservationDate.isEmpty()) {
            return null;
        }
    	
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        
        // フォームの文字列を LocalDateTime に変換
        return LocalDateTime.parse(this.reservationDate, formatter);
    }
}