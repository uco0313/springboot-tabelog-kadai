package com.example.tabelogpage.entity;

import java.sql.Date;
import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "companies")
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // 会社ID (主キー)

    @Column(name = "company_name")
    private String companyName; 

    @Column(name = "representative_name")
    private String representativeName;

    @Column(name = "establishment_date")
    private Date establishmentDate; 

    @Column(name = "postal_code")
    private String postalCode; 

    @Column(name = "address")
    private String address;

    @Column(name = "business_details")
    private String businessDetails; // 事業内容 (TEXT -> String)

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt; 

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt; 
}