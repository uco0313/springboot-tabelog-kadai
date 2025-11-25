package com.example.tabelogpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogpage.entity.Category; 

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}