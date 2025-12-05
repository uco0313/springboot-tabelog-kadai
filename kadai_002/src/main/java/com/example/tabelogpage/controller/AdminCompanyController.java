package com.example.tabelogpage.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogpage.entity.Company;
import com.example.tabelogpage.form.CompanyEditForm;
import com.example.tabelogpage.repository.CompanyRepository;
import com.example.tabelogpage.service.CompanyService; 

@Controller
@RequestMapping("/admin/company") 
public class AdminCompanyController {
    private final CompanyRepository companyRepository;
    private final CompanyService companyService; // Service層を介して処理を行う

    // コンストラクタによる依存性注入
    public AdminCompanyController(
        CompanyRepository companyRepository,
        CompanyService companyService
    ) {
        this.companyRepository = companyRepository;
        this.companyService = companyService;
    }

    // 会社情報編集フォームの表示
    // URL: /admin/company/edit
    @GetMapping("/edit")
    public String edit(Model model) {
        // ID=1 の会社情報を取得
        Company company = companyRepository.findById(1).orElseThrow(() -> new RuntimeException("Company info not found."));
        
        // 会社エンティティの情報を使ってフォームオブジェクトを初期化
        CompanyEditForm companyEditForm = new CompanyEditForm();
        companyEditForm.setId(company.getId());
        companyEditForm.setCompanyName(company.getCompanyName());
        companyEditForm.setRepresentativeName(company.getRepresentativeName());
        companyEditForm.setEstablishmentDate(company.getEstablishmentDate());
        companyEditForm.setPostalCode(company.getPostalCode());
        companyEditForm.setAddress(company.getAddress());
        companyEditForm.setBusinessDetails(company.getBusinessDetails());
        
        model.addAttribute("companyEditForm", companyEditForm);
        
        return "admin/company/edit"; // テンプレート名
    }

    // 会社情報更新処理
    // URL: /admin/company/update
    @PostMapping("/update")
    public String update(@ModelAttribute @Validated CompanyEditForm companyEditForm, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        
        // 入力チェック（バリデーション）
        if (bindingResult.hasErrors()) {
            return "admin/company/edit"; // エラーがあればフォームに戻る
        }
        
        // Service層を呼び出して更新処理を実行
        companyService.update(companyEditForm);
        
        // 成功メッセージをリダイレクト先に渡す
        redirectAttributes.addFlashAttribute("successMessage", "会社情報を正常に更新しました。");
        
        // 会社概要ページ（/company）にリダイレクトして結果を確認させる
        return "redirect:/company";
    }
}