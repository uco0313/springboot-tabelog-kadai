package com.example.tabelogpage.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // 【★追加★】バリデーション結果の受け取り
import org.springframework.validation.annotation.Validated; // 【★追加★】バリデーション実行
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tabelogpage.entity.Category;
import com.example.tabelogpage.form.CategoryRegisterForm; // 【★追加★】フォームクラスのインポート
import com.example.tabelogpage.repository.CategoryRepository;

@Controller
@RequestMapping("/admin/categories")
public class AdminCategoryController {

    private final CategoryRepository categoryRepository;

    public AdminCategoryController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // 管理者用：カテゴリ一覧表示
    @GetMapping
    public String index(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("categories", categories);
        return "admin/categories/index";
    }

    // 管理者用：新規登録画面表示
    @GetMapping("/register")
    public String register(Model model) {
        // 【修正1】フォームクラスをモデルに追加
        model.addAttribute("categoryRegisterForm", new CategoryRegisterForm());
        // 【修正2】共通フォームテンプレートへ変更
        return "admin/categories/form"; 
    }

    // 管理者用：編集画面表示
    @GetMapping("/{id}/edit")
    public String edit(@PathVariable(name = "id") Integer id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("カテゴリが見つかりません"));
        
        // 【修正3】エンティティからフォームクラスへ値を詰め替える
        CategoryRegisterForm categoryRegisterForm = new CategoryRegisterForm();
        categoryRegisterForm.setName(category.getName());
        
        // 【修正4】編集時はIDとフォームクラスの両方をモデルに追加
        model.addAttribute("category", category); 
        model.addAttribute("categoryRegisterForm", categoryRegisterForm);
        
        // 【修正5】共通フォームテンプレートへ変更
        return "admin/categories/form"; 
    }

    // 管理者用：登録・更新処理
    // 【修正6】パスを/createと/updateに分割せず、1つのメソッドで処理するように修正
    @PostMapping(value = {"/create", "/{id}/update"})
    public String createOrUpdate(
        @PathVariable(name = "id", required = false) Integer id, // IDは更新時のみ存在する
        @ModelAttribute @Validated CategoryRegisterForm categoryRegisterForm, // バリデーション有効化
        BindingResult bindingResult, // バリデーション結果
        RedirectAttributes redirectAttributes, 
        Model model // バリデーション失敗時の再表示用
    ) {
        // バリデーションチェック
        if (bindingResult.hasErrors()) {
            // エラーがある場合、フォームを再表示するためにモデルにデータを追加
            
            // 更新時（IDがある場合）はCategoryエンティティも必要
            if (id != null) {
                Category category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("カテゴリが見つかりません"));
                model.addAttribute("category", category);
            }
            // 共通フォームテンプレートへ戻る
            return "admin/categories/form";
        }
        
        // 【修正7】Categoryエンティティの生成と更新ロジック
        Category category;
        if (id == null) {
            // 新規登録の場合
            category = new Category();
        } else {
            // 更新の場合
            category = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("カテゴリが見つかりません"));
        }
        
        category.setName(categoryRegisterForm.getName());
        
        categoryRepository.save(category);
        redirectAttributes.addFlashAttribute("successMessage", "カテゴリを保存しました。");
        return "redirect:/admin/categories";
    }


    // 管理者用：削除処理 (変更なし)
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable(name = "id") Integer id, RedirectAttributes redirectAttributes) {
        categoryRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "カテゴリを削除しました。");
        return "redirect:/admin/categories";
    }
}