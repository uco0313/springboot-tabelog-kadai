package com.example.tabelogpage.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogpage.entity.Admin; 

public interface AdminRepository extends JpaRepository<Admin, Integer> {

}