package com.example.tabelogpage.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tabelogpage.entity.Company;
import com.example.tabelogpage.form.CompanyEditForm;
import com.example.tabelogpage.repository.CompanyRepository;

@Service
@Transactional
public class CompanyService {
    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public void update(CompanyEditForm companyEditForm) {
        // IDで既存のCompanyエンティティを取得
        // ID=1のデータは必ず存在すると仮定し、見つからない場合は例外をスローします。
    	
        Company company = companyRepository.findById(companyEditForm.getId())
                            .orElseThrow(() -> new RuntimeException("Company ID " + companyEditForm.getId() + " not found."));

        // フォームの情報でエンティティを更新
        company.setCompanyName(companyEditForm.getCompanyName());
        company.setRepresentativeName(companyEditForm.getRepresentativeName());
        company.setEstablishmentDate(companyEditForm.getEstablishmentDate());
        company.setPostalCode(companyEditForm.getPostalCode());
        company.setAddress(companyEditForm.getAddress());
        company.setBusinessDetails(companyEditForm.getBusinessDetails());

        companyRepository.save(company);
    }
}