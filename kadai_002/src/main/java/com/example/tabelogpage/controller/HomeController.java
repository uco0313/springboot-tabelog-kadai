package com.example.tabelogpage.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.tabelogpage.entity.Company;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.repository.CompanyRepository;
import com.example.tabelogpage.repository.StoreRepository;               

@Controller
public class HomeController {
    private final StoreRepository storeRepository;        
    private final CompanyRepository companyRepository;  
    

    public HomeController(StoreRepository storeRepository, CompanyRepository companyRepository) { 
        this.storeRepository = storeRepository;            
        this.companyRepository = companyRepository;          
    }    
    
    @GetMapping("/")
    public String index(Model model) {			
        List<Store> newStores = storeRepository.findTop5ByOrderByCreatedAtDesc();
        model.addAttribute("newStores", newStores);        
        
        return "index";
    }
    
    @GetMapping("/company")
    public String showCompanyPage(Model model) { // Modelオブジェクトを受け取るように修正
        // ID=1 の会社情報を取得する (ID=1は必ず存在すると仮定)
        Company company = companyRepository.findById(1).orElseThrow(() -> new RuntimeException("Company info not found."));
        
        // テンプレートに 'company' という名前でデータを渡す
        model.addAttribute("company", company); 
        
        return "company";
    }
}