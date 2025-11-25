package com.example.tabelogpage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogpage.entity.Member; 

public interface MemberRepository extends JpaRepository<Member, Integer> {

}