package com.example.tabelogpage.controller;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogpage.entity.Reservation;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.entity.User;
import com.example.tabelogpage.form.ReservationInputForm;
import com.example.tabelogpage.form.ReservationRegisterForm;
import com.example.tabelogpage.repository.ReservationRepository;
import com.example.tabelogpage.repository.StoreRepository;
import com.example.tabelogpage.security.UserDetailsImpl;
import com.example.tabelogpage.service.ReservationService;

@Controller
public class ReservationController {
    private final ReservationRepository reservationRepository;    
    private final StoreRepository storeRepository;
    private final ReservationService reservationService; 
    
    public ReservationController(ReservationRepository reservationRepository,StoreRepository storeRepository, ReservationService reservationService) {        
        this.reservationRepository = reservationRepository;      
        this.storeRepository = storeRepository;
        this.reservationService = reservationService;
    }    

    @GetMapping("/reservations")
    public String index(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl, @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable, Model model) {
        User user = userDetailsImpl.getUser();
        Page<Reservation> reservationPage = reservationRepository.findByUserOrderByCreatedAtDesc(user, pageable);
        
        model.addAttribute("reservationPage", reservationPage);          
        
        return "reservations/index";
    }
    

    @PostMapping("/stores/{id}/reservations/input")
    public String input(
        @PathVariable(name = "id") Integer id,
        @ModelAttribute @Validated ReservationInputForm reservationInputForm,
        BindingResult bindingResult, 
        RedirectAttributes redirectAttributes,
        Model model
    ) {    
        Store store = storeRepository.getReferenceById(id);
        
        // 1. 基本的なエラーをチェック
        if (bindingResult.hasErrors()) {            
            model.addAttribute("store", store);            
            return "stores/show"; 
        }
        
        // 2. 日時・定休日・営業時間など、業務ロジックに基づくチェック
        try {
            LocalDateTime reservationDateTime = reservationService.convertToLocalDateTime(
            		reservationInputForm.getReservationDate()
            );
            
            // 【過去日時チェック】予約日時が現在時刻より後かを確認
            if (reservationDateTime.isBefore(LocalDateTime.now().plusMinutes(1))) {
                bindingResult.rejectValue("reservationDate", "isFuture", "予約日時は現在時刻より後の日付を選択してください。");
            }

            // 【営業時間チェック】予約時間が営業時間内かを確認
            if (!reservationService.isReservable(reservationDateTime, store.getOpeningTime(), store.getClosingTime())) {
                bindingResult.rejectValue("reservationDate", "isReservable", "予約時間が営業時間外です。");
            }
            
            // 【定休日チェック】予約日が定休日ではないかを確認
            if (!reservationService.isNotRegularHoliday(reservationDateTime, store.getRegularHoliday())) {
                bindingResult.rejectValue("reservationDate", "isNotRegularHoliday", store.getRegularHoliday() + "は定休日です。");
            }
            

        } catch (Exception e) {
            bindingResult.rejectValue("reservationDate", "typeMismatch", "予約日時の形式が正しくありません。");
        }
        
        // 3. 業務ロジックのチェックでエラーがあれば、店舗詳細画面に戻る
        if (bindingResult.hasErrors()) {            
            model.addAttribute("store", store);            
            return "stores/show"; 
        }
        
        // ⭐ 追加: 定員チェック
        Integer requestedPeople = reservationInputForm.getNumberOfPeople();
        Integer capacity = store.getCapacity();
        
        if (requestedPeople > capacity) {
            // 定員オーバーエラーを追加
            bindingResult.rejectValue("numberOfPeople", "capacityViolation", 
                                      "予約人数が店舗の定員(" + capacity + "名)を超えています。");
        }
        
        // ⭐ 再チェック: 定員オーバーエラーがあれば、店舗詳細画面に戻る
        if (bindingResult.hasErrors()) {            
            model.addAttribute("store", store);            
            return "stores/show"; 
        }
        
        // 4. エラーがなければ、確認画面へリダイレクト
        ReservationRegisterForm reservationRegisterForm = new ReservationRegisterForm(
        	id, // storeId
        	null, // userId
        	reservationInputForm.getReservationDate(), // 予約日時
        	reservationInputForm.getNumberOfPeople()  // 予約人数
        );
        
        redirectAttributes.addFlashAttribute("reservationRegisterForm", reservationRegisterForm);            
        
        return "redirect:/stores/{id}/reservations/confirm";
    }
    
    @GetMapping("/stores/{id}/reservations/confirm")
    public String confirm(
        @PathVariable(name = "id") Integer id,
        @ModelAttribute ReservationRegisterForm reservationRegisterForm, 
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl,        
        Model model
    ) {
    
        Store store = storeRepository.getReferenceById(id); 
        User user = userDetailsImpl.getUser(); 
        
        // 登録時に必要な storeId と userId をセット
        reservationRegisterForm.setStoreId(store.getId()); 
        reservationRegisterForm.setUserId(user.getId());
        
        model.addAttribute("store", store);
        model.addAttribute("reservationRegisterForm", reservationRegisterForm);
        
        return "reservations/confirm"; 
    }
    
    @PostMapping("/reservations/create") 
    // RedirectAttributesを削除し、予約完了メッセージをURLパラメータで渡す
    public String create(@ModelAttribute ReservationRegisterForm reservationRegisterForm) {
        
        reservationService.create(reservationRegisterForm);
        
        // リダイレクト先のURLにクエリパラメータ（?reserved）を追加
        return "redirect:/reservations?reserved";
    }
    
    @PostMapping("/reservations/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        
        // サービス層の削除メソッドを呼び出す
        reservationService.delete(id);
        
        // キャンセル完了メッセージを設定
        redirectAttributes.addFlashAttribute("flashMessage", "予約をキャンセルしました。");
        
        // 予約一覧画面へリダイレクト
        return "redirect:/reservations";
    }
    
}