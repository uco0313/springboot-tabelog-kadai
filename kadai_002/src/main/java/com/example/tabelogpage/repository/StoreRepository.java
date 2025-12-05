package com.example.tabelogpage.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.tabelogpage.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Integer> {
    
    // --- 単独検索 ---
    // キーワード検索（店舗名のみ）
    public Page<Store> findByStoreNameLike(String storeName, Pageable pageable);
    // Pmax のみ
    public Page<Store> findByPriceMaxLessThanEqual(Integer priceMax, Pageable pageable); 
    // Pmin のみ
    public Page<Store> findByPriceMinGreaterThanEqual(Integer priceMin, Pageable pageable); 
    // Pmin, Pmax
    public Page<Store> findByPriceMinGreaterThanEqualAndPriceMaxLessThanEqual(Integer priceMin, Integer priceMax, Pageable pageable); // 価格帯

    
    
    // キーワード検索（店舗名 OR 住所）
    public Page<Store> findByStoreNameLikeOrAddressLike(String nameKeyword, String addressKeyword, Pageable pageable); 
    public Page<Store> findByAddressLike(String area, Pageable pageable); // エリア
    


    
    
    // C…カテゴリ　A…エリア　Pmin/Pmax…価格
    
    // --- カテゴリあり（C）の複合検索 ---
    public Page<Store> findByCategoryId(Integer categoryId, Pageable pageable); // C のみ
    
    // A, C
    public Page<Store> findByAddressLikeAndCategoryId(String area, Integer categoryId, Pageable pageable); 
    // Pmax, C
    public Page<Store> findByPriceMaxLessThanEqualAndCategoryId(Integer priceMax, Integer categoryId, Pageable pageable); 
    // Pmin, C
    public Page<Store> findByPriceMinGreaterThanEqualAndCategoryId(Integer priceMin, Integer categoryId, Pageable pageable); 
    // Pmin, Pmax, C
    public Page<Store> findByPriceMinGreaterThanEqualAndPriceMaxLessThanEqualAndCategoryId(Integer priceMin, Integer priceMax, Integer categoryId, Pageable pageable); 
    
    // A, Pmin, C
    public Page<Store> findByAddressLikeAndPriceMinGreaterThanEqualAndCategoryId(String area, Integer priceMin, Integer categoryId, Pageable pageable);
    // A, Pmax, C
    public Page<Store> findByAddressLikeAndPriceMaxLessThanEqualAndCategoryId(String area, Integer priceMax, Integer categoryId, Pageable pageable);
    
    // A, Pmin, Pmax, C の複合検索
    public Page<Store> findByAddressLikeAndPriceMinGreaterThanEqualAndPriceMaxLessThanEqualAndCategoryId(String area, Integer priceMin, Integer priceMax, Integer categoryId, Pageable pageable);
    
    
    // --- カテゴリなし（C=null）の複合検索 ---
    
    // A, Pmin
    public Page<Store> findByAddressLikeAndPriceMinGreaterThanEqual(String area, Integer priceMin, Pageable pageable);
    // A, Pmax
    public Page<Store> findByAddressLikeAndPriceMaxLessThanEqual(String area, Integer priceMax, Pageable pageable);
    
    // A, Pmin, Pmax の複合検索
    public Page<Store> findByAddressLikeAndPriceMinGreaterThanEqualAndPriceMaxLessThanEqual(String area, Integer priceMin, Integer priceMax, Pageable pageable);

    //新着順に10件取得するメソッド
    public List<Store> findTop5ByOrderByCreatedAtDesc();

}