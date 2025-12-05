package com.example.tabelogpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogpage.entity.Role;


public interface RoleRepository extends JpaRepository<Role, Integer> {
	public Role findByName(String name);  
}