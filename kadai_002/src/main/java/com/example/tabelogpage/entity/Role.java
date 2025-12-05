package com.example.tabelogpage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "roles")
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
        
    @Column(name = "name")
    private String name;       
    
 // ロール名の定数定義
    public static final String ROLE_FREE = "ROLE_FREE";
    public static final String ROLE_PAID = "ROLE_PAID";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
}