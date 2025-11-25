package com.example.tabelogpage.service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabelogpage.entity.Reservation;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.entity.User;
import com.example.tabelogpage.form.ReservationRegisterForm;
import com.example.tabelogpage.repository.ReservationRepository;
import com.example.tabelogpage.repository.StoreRepository;
import com.example.tabelogpage.repository.UserRepository;



@Service
public class ReservationService {
    
    private final ReservationRepository reservationRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    
    public ReservationService(ReservationRepository reservationRepository, StoreRepository storeRepository, UserRepository userRepository) {
        this.reservationRepository = reservationRepository;
        this.storeRepository = storeRepository;
        this.userRepository = userRepository;
    }
    
    /**
     * flatpickrから受け取った日付・時刻の文字列をLocalDateTimeに変換します。
     * @param reservationDateString "YYYY/MM/DD HH:MM" 形式の文字列
     * @return 変換後のLocalDateTimeオブジェクト
     */
    public LocalDateTime convertToLocalDateTime(String reservationDateString) {
        // flatpickrの dateFormat: "Y/m/d H:i" に対応するフォーマッターを定義
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        
        // 文字列をパースしてLocalDateTimeを返す
        return LocalDateTime.parse(reservationDateString, formatter);
    }


    /**
     * 予約日時が店舗の営業時間内にあるかを確認します。
     */
    public boolean isReservable(LocalDateTime reservationDate, LocalTime openingTime, LocalTime closingTime) {
        
        // 予約日時から「時刻」の部分のみを抽出する
        LocalTime reservationTime = reservationDate.toLocalTime();
        
        boolean isAfterOpening = reservationTime.isAfter(openingTime) || reservationTime.equals(openingTime);
        boolean isBeforeClosing = reservationTime.isBefore(closingTime);
        
        return isAfterOpening && isBeforeClosing;
    }
    
    /**
     * 予約日時が定休日ではないかを確認します。（定休日なら false を返す）
     */
    public boolean isNotRegularHoliday(LocalDateTime reservationDate, String regularHoliday) {
        
        // 予約日の曜日を取得する
        DayOfWeek dayOfWeek = reservationDate.getDayOfWeek();
        
        // DayOfWeekの値を日本語の曜日に変換する (例: MONDAY -> 月曜日)
        String dayOfWeekJp;
        switch (dayOfWeek) {
            case MONDAY:
                dayOfWeekJp = "月曜日";
                break;
            case TUESDAY:
                dayOfWeekJp = "火曜日";
                break;
            case WEDNESDAY:
                dayOfWeekJp = "水曜日";
                break;
            case THURSDAY:
                dayOfWeekJp = "木曜日";
                break;
            case FRIDAY:
                dayOfWeekJp = "金曜日";
                break;
            case SATURDAY:
                dayOfWeekJp = "土曜日";
                break;
            case SUNDAY:
                dayOfWeekJp = "日曜日";
                break;
            default:
                dayOfWeekJp = "";
                break;
        }
        
        // 取得した曜日が定休日（regularHoliday）に含まれているか確認する
        if (regularHoliday != null && regularHoliday.contains(dayOfWeekJp)) {
            return false; // 定休日と一致したので予約不可
        }
        
        return true; // 定休日と一致しなかったので予約可能
    }
    
    /**
     * 予約データを登録します。
     * @param reservationRegisterForm 登録フォーム
     */
    @Transactional
    public void create(ReservationRegisterForm reservationRegisterForm) {
        Reservation reservation = new Reservation();
        
        // IDを使って関連するエンティティを取得
        Store store = storeRepository.getReferenceById(reservationRegisterForm.getStoreId());
        User user = userRepository.getReferenceById(reservationRegisterForm.getUserId());
        
        // 日付文字列をLocalDateTimeに変換
        LocalDateTime reservationDateTime = convertToLocalDateTime(reservationRegisterForm.getReservationDate());
        
        // Entityに値をセット
        reservation.setStore(store);
        reservation.setUser(user);
        
        reservation.setReservationDate(reservationDateTime); 
        
        reservation.setNumberOfPeople(reservationRegisterForm.getNumberOfPeople());
        
        // データベースに保存
        reservationRepository.save(reservation);
    }
    
    /**
     * 指定されたIDの予約を削除します。（キャンセル機能）
     * @param id 予約ID
     */
    @Transactional // ⭐ 追加: 予約の削除処理
    public void delete(Integer id) {
        // IDを指定してデータベースから予約を削除
        reservationRepository.deleteById(id);
    }
}