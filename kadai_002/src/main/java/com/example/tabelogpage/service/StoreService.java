package com.example.tabelogpage.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.tabelogpage.entity.Category;
import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.form.StoreEditForm;
import com.example.tabelogpage.form.StoreRegisterForm;
import com.example.tabelogpage.repository.CategoryRepository;
import com.example.tabelogpage.repository.StoreRepository;

@Service
@Transactional
public class StoreService {
    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository; 
    
    // コンストラクタ
    public StoreService(StoreRepository storeRepository, CategoryRepository categoryRepository) {
        this.storeRepository = storeRepository;
        this.categoryRepository = categoryRepository;
    }
    
	public Optional<Store> findStoreById(Integer id) {
        return storeRepository.findById(id); 
    }
    
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }
    
   
     //検索条件に基づいて店舗を検索します。
    
    public Page<Store> findByCriteria(String keyword, String area, Integer priceMin, Integer priceMax, Integer categoryId, Pageable pageable) {
        
        Page<Store> storePage;

        // 検索条件フラグ
        boolean hasKeyword = (keyword != null && !keyword.isEmpty());
        boolean hasArea = (area != null && !area.isEmpty());
        boolean hasPriceMin = (priceMin != null);
        boolean hasPriceMax = (priceMax != null);
        boolean hasCategory = (categoryId != null);
        
        // クエリ用パラメータ
        String keywordForQuery = hasKeyword ? "%" + keyword + "%" : null;
        String areaForQuery = hasArea ? "%" + area + "%" : null;
        
        
        if (hasKeyword) {
            // 【キーワード単独検索】
            storePage = storeRepository.findByStoreNameLikeOrAddressLike(keywordForQuery, keywordForQuery, pageable);
            
        } else {
            // 【複合検索】: キーワードが存在しない場合、C, A, Pmin, Pmax の複合検索を実行

            if (hasCategory) {
                // --- カテゴリIDがある場合の複合検索ロジック（K キーワードなし） ---
                
                if (hasArea && hasPriceMin && hasPriceMax) {
                    storePage = storeRepository.findByAddressLikeAndPriceMinGreaterThanEqualAndPriceMaxLessThanEqualAndCategoryId(
                        areaForQuery, priceMin, priceMax, categoryId, pageable);
                } else if (hasArea  && hasPriceMin) {
                    storePage = storeRepository.findByAddressLikeAndPriceMinGreaterThanEqualAndCategoryId(areaForQuery, priceMin, categoryId, pageable);
                } else if (hasArea  && hasPriceMax) {
                    storePage = storeRepository.findByAddressLikeAndPriceMaxLessThanEqualAndCategoryId(areaForQuery, priceMax, categoryId, pageable);
                } else if (hasPriceMin && hasPriceMax) {
                    storePage = storeRepository.findByPriceMinGreaterThanEqualAndPriceMaxLessThanEqualAndCategoryId(priceMin, priceMax, categoryId, pageable);
                } else if (hasArea ) {
                    storePage = storeRepository.findByAddressLikeAndCategoryId(areaForQuery, categoryId, pageable);
                } else if (hasPriceMax) {
                    storePage = storeRepository.findByPriceMaxLessThanEqualAndCategoryId(priceMax, categoryId, pageable);
                } else if (hasPriceMin) {
                    storePage = storeRepository.findByPriceMinGreaterThanEqualAndCategoryId(priceMin, categoryId, pageable);
                } else {
                    storePage = storeRepository.findByCategoryId(categoryId, pageable);
                }
                
            } else {
                // --- カテゴリIDがない場合の複合検索ロジック（K　キーワードなし） ---

                if (hasArea  && hasPriceMin && hasPriceMax) {
                    storePage = storeRepository.findByAddressLikeAndPriceMinGreaterThanEqualAndPriceMaxLessThanEqual(
                        areaForQuery, priceMin, priceMax, pageable);
                } else if (hasArea  && hasPriceMin) {
                    storePage = storeRepository.findByAddressLikeAndPriceMinGreaterThanEqual(areaForQuery, priceMin, pageable);
                } else if (hasArea  && hasPriceMax) {
                    storePage = storeRepository.findByAddressLikeAndPriceMaxLessThanEqual(areaForQuery, priceMax, pageable);
                } else if (hasPriceMin && hasPriceMax) {
                    storePage = storeRepository.findByPriceMinGreaterThanEqualAndPriceMaxLessThanEqual(priceMin, priceMax, pageable);
                } else if (hasArea ) {
                    storePage = storeRepository.findByAddressLike(areaForQuery, pageable);
                } else if (hasPriceMax) {
                    storePage = storeRepository.findByPriceMaxLessThanEqual(priceMax, pageable);
                } else if (hasPriceMin) {
                    storePage = storeRepository.findByPriceMinGreaterThanEqual(priceMin, pageable);
                } else {
                    storePage = storeRepository.findAll(pageable);
                }
            }
        }
        
        return storePage;
    }
		
    
    /**
     * CSV出力のために、すべての店舗情報（List形式）を取得します。
     */
    @Transactional(readOnly = true) // 読み取り専用トランザクション
    public List<Store> findAllStoresList() {
        // JpaRepositoryが提供するfindAll()を使用して全件取得
        return storeRepository.findAll();
    }
    // ------------------------------------------
    
    
    public void create(StoreRegisterForm storeRegisterForm) { 
        Store store = new Store();
        
   
        Category category = categoryRepository.getReferenceById(storeRegisterForm.getCategoryId());
        store.setCategory(category); 
       
        
        MultipartFile imageFile = storeRegisterForm.getImageFile();
        
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename(); 
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            store.setImagePath(hashedImageName);
        }
        
        
        store.setStoreName(storeRegisterForm.getName());                
        store.setDescription(storeRegisterForm.getDescription());
        
        store.setPriceMin(storeRegisterForm.getPriceMin());
        store.setPriceMax(storeRegisterForm.getPriceMax());
        
   
        store.setOpeningTime(LocalTime.parse(storeRegisterForm.getOpeningTime()));
        store.setClosingTime(LocalTime.parse(storeRegisterForm.getClosingTime()));
        
        store.setPostalCode(storeRegisterForm.getPostalCode());
        store.setAddress(storeRegisterForm.getAddress());
        store.setPhoneNumber(storeRegisterForm.getPhoneNumber());
        
        store.setRegularHoliday(storeRegisterForm.getRegularHoliday());
                                
        storeRepository.save(store);
    } 
    
    @Transactional
    public void update(StoreEditForm storeEditForm) {
        Store store = storeRepository.getReferenceById(storeEditForm.getId());
        MultipartFile imageFile = storeEditForm.getImageFile();
        
        if (!imageFile.isEmpty()) {
            String imageName = imageFile.getOriginalFilename(); 
            String hashedImageName = generateNewFileName(imageName);
            Path filePath = Paths.get("src/main/resources/static/storage/" + hashedImageName);
            copyImageFile(imageFile, filePath);
            
            store.setImagePath(hashedImageName); 
        }
        
        store.setStoreName(storeEditForm.getStoreName());                
        store.setDescription(storeEditForm.getDescription());
        store.setPriceMin(storeEditForm.getPriceMin());
        store.setPriceMax(storeEditForm.getPriceMax());

        Category category = categoryRepository.getReferenceById(storeEditForm.getCategoryId());
        store.setCategory(category); 

        store.setOpeningTime(LocalTime.parse(storeEditForm.getOpeningTime())); 
        store.setClosingTime(LocalTime.parse(storeEditForm.getClosingTime())); 
        store.setPostalCode(storeEditForm.getPostalCode());
        store.setAddress(storeEditForm.getAddress());
        store.setPhoneNumber(storeEditForm.getPhoneNumber());
        store.setRegularHoliday(storeEditForm.getRegularHoliday()); 
                    
        storeRepository.save(store);
    }    
    
    public String generateNewFileName(String fileName) {
        // 1. 最後のドットの位置を見つけます
        int lastDotIndex = fileName.lastIndexOf(".");
        
        // 2. 拡張子を取得します (ドットがない場合は空文字列)
        String extension = (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex);
        
        // 3. UUIDで生成した一意な文字列と拡張子を結合します
        String hashedFileName = UUID.randomUUID().toString() + extension;
        
        return hashedFileName;
    } 
    
    
    public void copyImageFile(MultipartFile imageFile, Path filePath) {           
        try {
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING); 
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("ファイルのコピー中にエラーが発生しました。", e);
        }         
    } 
}