package com.example.tabelogpage.controller;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tabelogpage.entity.Store;
import com.example.tabelogpage.service.StoreService;

import jakarta.servlet.http.HttpServletResponse;

/**
 * 店舗情報（Store）をCSVファイルとしてダウンロードするためのコントローラー
 */
@RestController
@RequestMapping("/admin/stores")
public class CsvDownloadController {

    private final StoreService storeService;

    public CsvDownloadController(StoreService storeService) {
        this.storeService = storeService;
    }

    /**
     * CSVファイルを生成し、HTTPレスポンスとしてクライアントに返す
     */
    @GetMapping("/export/csv")
    public void downloadStoresCsv(HttpServletResponse response) throws IOException {

        // 1. レスポンスヘッダーの設定 (ファイル名とMIMEタイプ)
        String filename = "store_list_" + System.currentTimeMillis() + ".csv";
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        
        // 【文字化け対策】UTF-8 & BOM付き
        response.setCharacterEncoding("UTF-8");
        response.getOutputStream().write(0xEF); 
        response.getOutputStream().write(0xBB);
        response.getOutputStream().write(0xBF);

        // 2. CSVライターの準備とデータ書き込み
        // try-with-resources構文で、自動でリソースをクローズ

       
        CSVFormat csvFormat = CSVFormat.Builder.create(CSVFormat.DEFAULT)
            .setHeader("ID", "店舗名", "郵便番号", "住所", "電話番号", "営業時間", "休業日", "説明", "カテゴリ名")
            .setQuoteMode(QuoteMode.ALL)
            .build();
  

        try (
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
            // CSVPrinterを初期化する際に、上で作成したcsvFormatを渡す
            CSVPrinter csvPrinter = new CSVPrinter(writer, csvFormat)
        ) {
            // 3. データ取得 (StoreServiceに追加したメソッドを使用)
            List<Store> stores = storeService.findAllStoresList();

            // 4. 1レコードずつCSVに書き込む
            for (Store store : stores) {
                csvPrinter.printRecord(
                    store.getId(), 
                    store.getStoreName(), 
                    store.getPostalCode(), 
                    store.getAddress(), 
                    store.getPhoneNumber(),
                    store.getOpeningTime() + "〜" + store.getClosingTime(),
                    store.getRegularHoliday(),
                    store.getDescription(),
                    store.getCategory() != null ? store.getCategory().getName() : ""
                );
            }

            csvPrinter.flush();
        } 
    }
}