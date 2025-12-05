package com.example.tabelogpage.form;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CompanyEditForm {
    @NotNull
    private Integer id; // 会社ID (通常は1)

    @NotBlank(message = "会社名を入力してください。")
    private String companyName; 

    @NotBlank(message = "代表者名を入力してください。")
    private String representativeName;

    @NotNull(message = "設立日を入力してください。")
    private Date establishmentDate; 

    @NotBlank(message = "郵便番号を入力してください。")
    private String postalCode; 

    @NotBlank(message = "所在地を入力してください。")
    private String address;

    @NotBlank(message = "事業内容を入力してください。")
    private String businessDetails;
}