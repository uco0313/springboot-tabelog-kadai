package com.example.tabelogpage.repository;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.tabelogpage.entity.Reservation;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.entity.User;


public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
	public Page<Reservation> findByUserOrderByCreatedAtDesc(User user, Pageable pageable);
    
    /**
     * 指定した店舗で、指定した予約日時と一致する予約の人数（numberOfPeople）の合計を取得。
     * 予約が存在しない場合は NULL を返す。（Service側で 0 に変換）
     */
    @Query("SELECT SUM(r.numberOfPeople) FROM Reservation r WHERE r.store = :store AND r.reservationDate = :reservationDate")
    Integer sumNumberOfPeopleByStoreAndReservationDate(@Param("store") Store store, @Param("reservationDate") LocalDateTime reservationDate);
   
}