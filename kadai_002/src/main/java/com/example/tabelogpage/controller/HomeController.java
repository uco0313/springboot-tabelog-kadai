package com.example.tabelogpage.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.tabelogpage.entity.Category;
import com.example.tabelogpage.entity.Company;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.repository.CategoryRepository;
import com.example.tabelogpage.repository.CompanyRepository;
import com.example.tabelogpage.repository.StoreRepository;

@Controller
public class HomeController {
    private final StoreRepository storeRepository;
    private final CompanyRepository companyRepository;
    private final CategoryRepository categoryRepository;

    public HomeController(
        StoreRepository storeRepository, 
        CompanyRepository companyRepository,
        CategoryRepository categoryRepository
    ) {
        this.storeRepository = storeRepository;
        this.companyRepository = companyRepository;
        this.categoryRepository = categoryRepository;
    }

    // トップページ表示メソッド
    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        // ページングオブジェクトの作成とModelへの追加
        Pageable pageable = PageRequest.of(page, 10);
        Page<Store> storePage = storeRepository.findAll(pageable);
        model.addAttribute("storePage", storePage);

        // 既存の新着店舗の取得
        List<Store> newStores = storeRepository.findTop5ByOrderByCreatedAtDesc();
        model.addAttribute("newStores", newStores);

        // カテゴリ検索機能のために全カテゴリ情報を取得
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);

        return "index";
    }

    // 会社概要ページ表示メソッド
    @GetMapping("/company")
    public String showCompanyPage(Model model) {
        // ID=1 の会社情報を取得する (ID=1は必ず存在すると仮定)
        Company company = companyRepository.findById(1).orElseThrow(() -> new RuntimeException("Company info not found."));

        // テンプレートに 'company' という名前でデータを渡す
        model.addAttribute("company", company);

        return "company";
    }
}