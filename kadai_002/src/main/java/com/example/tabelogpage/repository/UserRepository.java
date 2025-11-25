package com.example.tabelogpage.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogpage.entity.User;


public interface UserRepository extends JpaRepository<User, Integer> {	
	public User findByEmail(String email);	
	
    // 氏名、フリガナ、メールアドレスの3つのフィールドで検索するメソッドに置き換える（または追加する）
	public Page<User> findByNameLikeOrFuriganaLikeOrEmailLike(String nameKeyword, String furiganaKeyword, String emailKeyword, Pageable pageable);
	
}