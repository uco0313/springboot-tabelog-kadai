-- 外部キー制約を一時的に無効化
SET FOREIGN_KEY_CHECKS = 0; 

-- 各テーブルのAUTO_INCREMENTの現在値をリセット
-- (この処理は、データ挿入時にIDがずれるのを防ぐために重要です)
ALTER TABLE admin AUTO_INCREMENT = 1;
ALTER TABLE categories AUTO_INCREMENT = 1;
ALTER TABLE companies AUTO_INCREMENT = 1;
ALTER TABLE members AUTO_INCREMENT = 1;
ALTER TABLE roles AUTO_INCREMENT = 1;
ALTER TABLE users AUTO_INCREMENT = 1;
ALTER TABLE verification_tokens AUTO_INCREMENT = 1;
ALTER TABLE password_reset_tokens AUTO_INCREMENT = 1;
ALTER TABLE stores AUTO_INCREMENT = 1;
ALTER TABLE reservations AUTO_INCREMENT = 1;
ALTER TABLE reviews AUTO_INCREMENT = 1;

-- #################################
-- 1. categories テーブル
-- #################################
INSERT IGNORE INTO categories (id, name) VALUES (1, '和食 Japanese food');
INSERT IGNORE INTO categories (id, name) VALUES (2, 'イタリアン Italian food');
INSERT IGNORE INTO categories (id, name) VALUES (3, '中華 Chinese food');
INSERT IGNORE INTO categories (id, name) VALUES (4, 'カフェ Cafe');
INSERT IGNORE INTO categories (id, name) VALUES (5, 'エスニック Ethnic food');
INSERT IGNORE INTO categories (id, name) VALUES (6, 'その他 Others');



-- #################################
-- 2. admin テーブル
-- #################################
INSERT IGNORE INTO admin (id, email, password_hash) 
VALUES (1, 'admin@example.com', '$2a$10$AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA');

-- #################################
-- 3. companies テーブル
-- #################################
INSERT IGNORE INTO companies (id, company_name, representative_name, establishment_date, postal_code, address, business_details)
VALUES (1, 'NAGOYAMESHI株式会社', '代表取締役 北村　美桜', '2010-04-01', '150-0043', '愛知県名古屋市中村区1-1-1 NAGOYAMESHI TOWER 1101', '飲食店の情報提供サービス事業');


--- #################################
-- 4. stores テーブル 
-- #################################

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (1, 1, '銀座 匠の寿司', 'store001.jpg', '厳選された旬の魚を提供する高級寿司店。', 10000, 20000, '17:00:00', '22:00:00', '450-0001', '愛知県名古屋市千種区銀座1-2-3', '052-1234-5678', '月曜日', 20, '2025-07-12 09:15:20', '2025-07-12 09:15:20');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (2, 2, 'トラットリア・リッコ', 'store002.jpg', '本格的な南イタリア料理を楽しめるアットホームな店。', 3000, 6000, '18:00:00', '23:00:00', '450-0002', '愛知県名古屋市中村区六本木4-5-6', '052-9876-5432', '日曜日', 15, '2025-03-28 14:40:05', '2025-03-28 14:40:05');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (3, 3, '中華料理 龍鳳', 'store003.jpg', '四川料理をベースにした辛さと旨さが特徴の老舗。', 2000, 4000, '11:30:00', '21:00:00', '450-0003', '愛知県名古屋市中区西新宿7-8-9', '052-5555-4444', '無休', 25, '2025-09-01 18:05:45', '2025-09-01 18:05:45');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (4, 4, 'Book Cafe moumou', 'store004.jpg', '静かな空間で読書を楽しめる隠れ家的カフェ。', 1000, 2000, '09:00:00', '18:00:00', '450-0004', '愛知県名古屋市中川区渋谷1-1-1', '052-3333-2222', '火曜日', 10, '2025-01-05 11:30:10', '2025-01-05 11:30:10');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (5, 1, '名駅前 海鮮問屋', 'store005.jpg', '新鮮な魚介をリーズナブルに提供。ランチも人気。', 4000, 8000, '11:00:00', '23:00:00', '450-0005', '愛知県名古屋市西区駅前3-1-1', '052-1234-5500', '水曜日', 30, '2025-10-18 17:55:50', '2025-10-18 17:55:50');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (6, 2, 'ピッツェリア・ロー二', 'store006.jpg', '石窯で焼く本格ナポリピッツァ専門店。', 2500, 4500, '17:30:00', '22:30:00', '450-0006', '愛知県名古屋市東区栄5-12-1', '052-9876-5501', '火曜日', 18, '2025-04-03 08:00:00', '2025-04-03 08:00:00');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (7, 3, '広東飯店 口福', 'store007.jpg', '家族連れに人気の、あっさりとした広東料理。', 1500, 3500, '11:00:00', '21:30:00', '450-0007', '愛知県名古屋市北区丸の内2-8-3', '052-5555-4402', '木曜日', 22, '2025-08-22 13:10:35', '2025-08-22 13:10:35');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (8, 4, 'COFFEEEEEE', 'store008.jpg', '自家焙煎コーヒーと焼き菓子が楽しめる。', 800, 1500, '08:00:00', '17:00:00', '450-0008', '愛知県名古屋市南区港町1-4-7', '052-3333-2203', '金曜日', 12, '2025-05-19 16:35:15', '2025-05-19 16:35:15');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (9, 1, '寿司居酒屋 大漁', 'store009.jpg', '寿司と豊富な居酒屋メニューが楽しめる。', 3500, 7000, '17:00:00', '00:00:00', '450-0009', '愛知県名古屋市昭和区桜通6-2-2', '052-1111-9904', '無休', 28, '2025-02-14 10:25:55', '2025-02-14 10:25:55');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (10, 2, 'リストランテ 月と星', 'store010.jpg', '夜景が見えるロマンチックな高級イタリアン。', 8000, 15000, '17:00:00', '23:00:00', '450-0010', '愛知県名古屋市瑞穂区星ヶ丘1-10', '052-8888-7705', '月曜日', 14, '2025-11-04 15:50:25', '2025-11-04 15:50:25');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (11, 3, '餃子専門店 チャンソン', 'store011.jpg', 'パリパリの羽付き餃子が自慢。', 1200, 2500, '11:00:00', '20:00:00', '450-0011', '愛知県名古屋市熱田区神宮3-5', '052-7777-6606', '水曜日', 17, '2025-06-25 12:45:00', '2025-06-25 12:45:00');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (12, 4, '抹茶と甘味処 nagi', 'store012.jpg', '伝統的な和菓子と抹茶を提供する専門店。', 900, 1800, '10:00:00', '18:00:00', '450-0012', '愛知県名古屋市中川区山王通4-1-1', '052-6666-5507', '火曜日', 11, '2025-01-29 09:50:30', '2025-01-29 09:50:30');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (13, 1, '回転寿司 鮮魚亭', 'store013.jpg', '家族で楽しめるリーズナブルな回転寿司。', 1500, 3000, '11:00:00', '22:00:00', '450-0013', '愛知県名古屋市港区大江町7-15', '052-4444-3308', '無休', 25, '2025-09-17 19:20:10', '2025-09-17 19:20:10');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (14, 2, 'オステリア ドルチェ', 'store014.jpg', '手打ちパスタと種類豊富なデザートが人気。', 4500, 7500, '18:00:00', '23:00:00', '450-0014', '愛知県名古屋市天白区植田西3-8', '052-2222-1109', '木曜日', 16, '2025-03-09 07:35:55', '2025-03-09 07:35:55');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (15, 3, '四川麻婆 炎', 'store015.jpg', '本場仕込みの激辛麻婆豆腐が名物。', 2800, 5000, '17:00:00', '22:00:00', '450-0015', '愛知県名古屋市守山区小幡中2-1-1', '052-0000-0010', '日曜日', 21, '2025-10-06 14:00:20', '2025-10-06 14:00:20');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (16, 4, 'terrace moon', 'store016.jpg', '開放的なテラス席でブランチを楽しめる。', 1200, 2500, '09:30:00', '19:00:00', '450-0016', '愛知県名古屋市緑区大高町字平子38', '052-9999-8811', '月曜日', 13, '2025-05-01 11:15:45', '2025-05-01 11:15:45');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (17, 1, '高級天ぷら 葵', 'store017.jpg', '旬の食材を使った揚げたての天ぷらを提供。', 9000, 18000, '17:30:00', '22:00:00', '450-0017', '愛知県名古屋市中区錦3-1-1', '052-1234-0012', '火曜日', 18, '2025-08-08 17:25:35', '2025-08-08 17:25:35');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (18, 2, '地中海レストラン Baro', 'store018.jpg', '魚介類を中心とした地中海料理レストラン。', 5000, 9000, '18:00:00', '23:30:00', '450-0018', '愛知県名古屋市熱田区金山町1-1-1', '052-9876-0013', '水曜日', 23, '2025-02-20 18:40:50', '2025-02-20 18:40:50');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (19, 3, '上海小籠包 匠', 'store019.jpg', 'ジューシーな小籠包と点心が人気。', 2500, 4500, '11:00:00', '21:00:00', '450-0019', '愛知県名古屋市昭和区広路町8-8', '052-5555-0014', '木曜日', 15, '2025-10-15 13:55:05', '2025-11-15 13:55:05');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (20, 4, '古民家喫茶 コテジ', 'store020.jpg', 'レトロな雰囲気の古民家で静かに過ごせる。', 700, 1300, '10:00:00', '16:00:00', '450-0020', '愛知県名古屋市瑞穂区弥富通1-10', '052-3333-0015', '金曜日', 10, '2025-04-16 10:10:40', '2025-04-16 10:10:40');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (21, 1, 'うなぎ処 蓬来軒', 'store021.jpg', '名古屋名物のひつまぶしを堪能できる老舗。', 6000, 12000, '11:00:00', '20:30:00', '450-0021', '愛知県名古屋市千種区覚王山通8-10', '052-1111-0016', '月曜日', 26, '2025-07-03 16:20:55', '2025-07-03 16:20:55');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (22, 2, 'トラットリア フォルテ', 'store022.jpg', '陽気な雰囲気でワインと料理が楽しめる店。', 3500, 6500, '18:00:00', '23:00:00', '450-0022', '愛知県名古屋市中村区則武1-1-1', '052-8888-0017', '火曜日', 19, '2025-01-11 12:35:15', '2025-01-11 12:35:15');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (23, 3, '本場香港麺専門店', 'store023.jpg', 'あっさりとしたスープと細麺が特徴の香港麺。', 1000, 2000, '11:00:00', '20:00:00', '450-0023', '愛知県名古屋市中区大須3-1-1', '052-7777-0018', '水曜日', 15, '2025-09-29 08:50:30', '2025-09-29 08:50:30');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (24, 4, 'pancake tabetai', 'store024.jpg', 'ふわふわのパンケーキがSNSで話題。', 1500, 3000, '10:00:00', '19:00:00', '450-0024', '愛知県名古屋市北区金城1-5-1', '052-6666-0019', '木曜日', 14, '2025-03-18 15:05:45', '2025-03-18 15:05:45');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (25, 1, '炉端焼き 漁火', 'store025.jpg', '新鮮な魚介を炉端焼きで提供する居酒屋。', 4500, 8500, '17:00:00', '00:00:00', '450-0025', '愛知県名古屋市西区栄生3-2-2', '052-4444-0020', '無休', 28, '2025-10-24 19:40:00', '2025-10-24 19:40:00');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (26, 2, 'イタリアンバル ', 'store026.jpg', '気軽に立ち寄れるイタリアンバール。タパスが充実。', 2000, 4000, '17:00:00', '01:00:00', '450-0026', '愛知県名古屋市東区筒井3-1-1', '052-2222-0021', '日曜日', 20, '2025-06-08 09:30:25', '2025-06-08 09:30:25');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (27, 3, '台湾ラーメン 味仙', 'store027.jpg', '名古屋名物台湾ラーメンの元祖。激辛が人気。', 1200, 2500, '18:00:00', '02:00:00', '450-0027', '愛知県名古屋市昭和区御器所1-1-1', '052-0000-0022', '月曜日', 24, '2025-02-05 13:25:10', '2025-02-05 13:25:10');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (28, 4, 'book book cafe', 'store028.jpg', '専門書と静かな読書スペースを提供。', 1000, 2000, '09:00:00', '20:00:00', '450-0028', '愛知県名古屋市南区呼続4-1-1', '052-9999-0023', '水曜日', 10, '2025-08-14 10:40:50', '2025-08-14 10:40:50');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (29, 1, 'おでん専門店 暖', 'store029.jpg', '冬場に嬉しい、優しい出汁のおでん。', 3000, 5000, '17:00:00', '23:00:00', '450-0029', '愛知県名古屋市瑞穂区高田町1-1-1', '052-1234-0024', '火曜日', 18, '2025-05-27 17:50:35', '2025-05-27 17:50:35');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (30, 2, 'ワイン食堂 ソレイユ', 'store030.jpg', '自然派ワインと創作イタリアンが楽しめる。', 4000, 7000, '18:00:00', '00:00:00', '450-0030', '愛知県名古屋市熱田区六野1-1-1', '052-9876-0025', '木曜日', 27, '2025-10-28 14:15:55', '2025-11-28 14:15:55');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (31, 3, '本格中華 百楽', 'store031.jpg', '接待にも使える高級感のある本格中華。', 5000, 10000, '17:00:00', '22:00:00', '450-0031', '愛知県名古屋市中川区八田町4-1-1', '052-5555-0026', '金曜日', 29, '2025-07-20 07:45:10', '2025-07-20 07:45:10');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (32, 4, '隠れ家紅茶専門店', 'store032.jpg', '世界中の紅茶と優雅なティータイム。', 1800, 3500, '11:00:00', '18:00:00', '450-0032', '愛知県名古屋市港区入場町1-1-1', '052-3333-0027', '土曜日', 12, '2025-01-19 18:30:45', '2025-01-19 18:30:45');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (33, 1, '寿司割烹 粋', 'store033.jpg', '会席料理も楽しめる、落ち着いた雰囲気の寿司割烹。', 12000, 30000, '17:00:00', '21:00:00', '450-0033', '愛知県名古屋市天白区原1-1-1', '052-1111-0028', '月曜日', 15, '2025-11-16 17:13:52', '2025-11-16 18:13:52');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (34, 2, 'イタリア食堂 マンマ', 'store034.jpg', 'お母さんの味を再現した温かい家庭料理。', 2000, 4000, '11:30:00', '20:00:00', '450-0034', '愛知県名古屋市守山区廿軒家1-1-1', '052-8888-0029', '水曜日', 18, '2025-03-01 16:05:25', '2025-03-01 16:05:25');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (35, 3, '中華粥 優', 'store035.jpg', '朝食にもぴったりの、胃に優しい中華粥。', 800, 1500, '08:00:00', '14:00:00', '450-0035', '愛知県名古屋市緑区鹿山1-1-1', '052-7777-0030', '火曜日', 10, '2025-10-11 12:20:30', '2025-10-11 12:20:30');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (36, 4, 'カフェ レトロ レトロ', 'store036.jpg', '大正ロマンを感じるアンティーク調のカフェ。', 900, 1800, '10:00:00', '18:00:00', '450-0036', '愛知県名古屋市中区丸の内3-1-1', '052-6666-0031', '木曜日', 13, '2025-04-22 08:10:50', '2025-04-22 08:10:50');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (37, 1, '焼鳥と日本酒 鶏次郎', 'store037.jpg', '新鮮な鶏を使った焼鳥と豊富な地酒。', 3500, 6000, '17:00:00', '23:00:00', '450-0037', '愛知県名古屋市熱田区桜田町1-1-1', '052-4444-0032', '金曜日', 22, '2025-08-30 19:35:15', '2025-08-30 19:35:15');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (38, 2, 'パスタ専門店 麺工房', 'store038.jpg', '豊富な種類のソースと手打ち生パスタ。', 1500, 3500, '11:00:00', '21:00:00', '450-0038', '愛知県名古屋市昭和区八事本町1-1-1', '052-2222-0033', '日曜日', 16, '2025-05-10 13:40:20', '2025-05-10 13:40:20');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (39, 3, '薬膳火鍋 健康美', 'store039.jpg', '美容と健康を意識した薬膳スープの火鍋。', 4000, 8000, '17:30:00', '22:30:00', '450-0039', '愛知県名古屋市瑞穂区中山町1-1-1', '052-0000-0034', '月曜日', 28, '2025-02-25 10:55:40', '2025-02-25 10:55:40');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (40, 4, 'Smoothie smoothie', 'store040.jpg', '新鮮な野菜と果物を使ったデトックスドリンク。', 700, 1500, '08:30:00', '19:30:00', '450-0040', '愛知県名古屋市熱田区伝馬1-1-1', '052-9999-0035', '水曜日', 11, '2025-10-20 17:10:55', '2025-11-20 17:10:55');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (41, 1, '自然派和食 結', 'store041.jpg', '無農薬野菜と出汁にこだわった健康的な定食屋。', 1500, 3000, '11:00:00', '21:00:00', '450-0041', '愛知県名古屋市千種区富士見台15', '052-1111-1111', '日曜日', 20, '2025-11-15 17:13:51', '2025-11-15 18:13:51');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (42, 4, 'オーガニックカフェ muku', 'store042.jpg', '無添加食材にこだわった、体に優しいカフェメニュー。', 1000, 2500, '09:00:00', '19:00:00', '450-0042', '愛知県名古屋市中村区大日町2-2-2', '052-2222-2222', '水曜日', 12, '2025-01-23 09:45:35', '2025-01-23 09:45:35');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (43, 3, '本場マーラータン 辛王', 'store043.jpg', 'カスタマイズできる具材が魅力の人気のマーラータン専門店。', 1200, 2800, '11:30:00', '22:00:00', '450-0043', '愛知県名古屋市中区栄3-3-3', '052-3333-3333', '無休', 25, '2025-11-14 17:13:50', '2025-11-14 18:13:50');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (44, 4, 'アサイー専門店 COCO', 'store044.jpg', '新鮮なフルーツとアサイーボウル、スムージーを提供。', 800, 1800, '10:00:00', '18:00:00', '450-0044', '愛知県名古屋市中川区山王1-4-4', '052-4444-4444', '火曜日', 10, '2025-11-13 17:13:49', '2025-11-13 18:13:49');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (45, 4, 'タピオカドリンク tappi', 'store045.jpg', '様々なフレーバーのタピオカドリンクが楽しめるスタンド。', 600, 1000, '12:00:00', '20:00:00', '450-0045', '愛知県名古屋市北区黒川本通5-5-5', '052-5555-5555', '月曜日', 15, '2025-10-30 18:25:20', '2025-10-30 18:25:20');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (46, 5, 'spice spice', 'store046.jpg', '日替わりスパイスカレーと副菜が人気の専門店。', 1200, 2500, '11:00:00', '15:00:00', '450-0046', '愛知県名古屋市昭和区川名本町11', '052-6666-6666', '火曜日', 20, '2025-11-17 17:13:53', '2025-11-17 18:13:53');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (47, 5, 'インドスパイスの王様', 'store047.jpg', '南インド風の本格的なミールスを提供するカレー屋。', 1500, 3000, '11:30:00', '21:30:00', '450-0047', '愛知県名古屋市西区上小田井2-2-2', '052-7777-7777', '月曜日', 22, '2025-08-01 07:50:00', '2025-08-01 07:50:00');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (48, 5, '欧風カレーといえば、ここ。', 'store048.jpg', '長時間煮込んだ濃厚なルーが特徴の欧風カレー専門店。', 1800, 3500, '11:00:00', '20:00:00', '450-0048', '愛知県名古屋市東区東桜4-4-4', '052-8888-8888', '無休', 30, '2025-05-05 16:55:25', '2025-05-05 16:55:25');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (49, 5, 'カレーと酒場 ナマステ', 'store049.jpg', '夜は多国籍な一品料理とクラフトビールも楽しめる。', 1000, 4000, '17:00:00', '23:00:00', '450-0049', '愛知県名古屋市中区錦1-5-5', '052-9999-9999', '日曜日', 25, '2025-02-10 19:00:30', '2025-02-10 19:00:30');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (50, 5, 'マイルドカレー こぐま', 'store050.jpg', '子どもから大人まで楽しめる辛さ控えめの優しいカレー。', 900, 1800, '11:00:00', '19:00:00', '450-0050', '愛知県名古屋市瑞穂区弥富ヶ丘5-6-7', '052-1234-9876', '木曜日', 18, '2025-10-16 13:35:40', '2025-11-16 13:35:40');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (51, 6, 'hamburger is .', 'store051.jpg', '自家製バンズ。チーズバーガーが1番人気。', 1000, 2000, '11:00:00', '19:00:00', '450-0051', '愛知県名古屋市東区希望ヶ丘58', '052-1234-9878', '木曜日', 30, '2025-10-17 13:40:40', '2025-11-16 13:35:40');

INSERT IGNORE INTO stores (id, category_id, store_name, image_path, description, price_min, price_max, opening_time, closing_time, postal_code, address, phone_number, regular_holiday, capacity, created_at, updated_at)
VALUES (52, 6, 'My name is TACOS.', 'store052.jpg', '本格メキシカン！名古屋上陸。', 2000, 5000, '17:00:00', '23:00:00', '450-0052', '愛知県名古屋市中村区ひばりヶ丘58', '052-1234-8878', '火曜日', 20, '2025-10-18 13:40:40', '2025-11-16 14:35:40');



-- #################################
-- 5. reviews テーブル
-- #################################

-- ID 1 (佐藤太郎)が銀座 匠の寿司（store_id=1）にレビュー
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (1, 1, 1, 5, '人生で一番美味しい寿司でした！');

INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (31, 2, 1, 5, '最高！');

INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (32, 3, 1, 5, '再訪！！');

INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (33, 4, 1, 5, '新鮮です');

INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (34, 5, 1, 5, 'おいしい～');

INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (35, 6, 1, 5, 'お酒が進みます');

INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (36, 7, 1, 4, 'また来たいです');

INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (37, 8, 1, 5, '牡蠣がおすすめです');

INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (38, 9, 1, 5, 'お通しがおいしい');

INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (39, 10, 1, 4, '予約必須');

INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (40, 11, 1, 4, '日替わりメニューがあります');





-- ID 2 (鈴木花子)がトラットリア・リッコ（store_id=2）にレビュー
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (2, 2, 2, 4, 'パスタが絶品！ワインも豊富でまた来たいです。');

-- ID 1 (佐藤太郎)が中華料理 龍鳳（store_id=3）にレビュー
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (3, 1, 3, 3, '担々麺が美味しかったです。辛党にお勧め！');

-- カフェ (ID: 4, 8, 12, 16, 20, 24, 28, 32, 36, 40, 42, 44, 45)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (4, 2, 4, 5, '静かで居心地が良い最高のカフェです。読書が進みました。'); -- Book Cafe moumou (ID: 4)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (5, 3, 8, 4, '自家焙煎のコーヒーの香りが最高でした。焼き菓子も美味しい。'); -- COFFEEEEEE (ID: 8)

-- イタリアン (ID: 6, 10, 14)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (6, 1, 6, 5, 'ナポリピッツァは耳まで美味しく、まさに本場の味！'); -- ピッツェリア・ロー二 (ID: 6)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (7, 4, 10, 5, '夜景が綺麗で、デートにぴったりでした。'); -- リストランテ 月と星 (ID: 10)

-- 寿司/和食 (ID: 5, 9, 13)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (8, 2, 5, 4, 'ランチの海鮮丼が新鮮でボリューム満点。コスパが良い！'); -- 名駅前 海鮮問屋 (ID: 5)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (9, 3, 9, 3, '居酒屋メニューが豊富で、団体でワイワイ楽しめました。'); -- 寿司居酒屋 大漁 (ID: 9)

-- 中華 (ID: 7, 11, 15)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (10, 1, 7, 4, '広東料理はあっさりしていて、子供も食べやすかったです。'); -- 広東飯店 口福 (ID: 7)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (11, 4, 15, 5, '名物の激辛麻婆豆腐は想像以上の辛さ！中毒性があります。'); -- 四川麻婆 炎 (ID: 15)

-- カフェ
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (12, 2, 12, 5, '抹茶の苦味が絶妙で、和菓子との組み合わせが最高でした。'); -- 抹茶と甘味処 nagi (ID: 12)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (13, 1, 16, 4, 'テラス席が開放的で気持ちよく、ブランチがおしゃれでした。'); -- terrace moon (ID: 16)

-- 寿司/和食
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (14, 3, 17, 5, '揚げたての天ぷらはサクサクで、素材の味がしっかり生きていました。'); -- 高級天ぷら 葵 (ID: 17)

-- イタリアン
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (15, 2, 18, 4, '魚介が新鮮で、地中海料理の味付けがとても良かったです。'); -- 地中海レストラン Baro (ID: 18)

-- 中華
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (16, 1, 19, 5, '小籠包から肉汁が溢れて、とてもジューシーでした。'); -- 上海小籠包 匠 (ID: 19)

-- カフェ
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (17, 4, 20, 3, 'レトロな雰囲気が素敵でしたが、少し狭いのが残念。'); -- 古民家喫茶 コテジ (ID: 20)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (18, 3, 24, 5, 'パンケーキはふわふわで、見た目も可愛くて満足！'); -- pancake tabetai (ID: 24)

-- 寿司/和食
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (19, 1, 21, 5, 'ひつまぶしは絶品！薬味と出汁で3度楽しめました。'); -- うなぎ処 蓬来軒 (ID: 21)

-- イタリアン
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (20, 2, 22, 4, 'ワインの種類が豊富で、陽気な雰囲気で楽しめました。'); -- トラットリア フォルテ (ID: 22)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (21, 4, 26, 3, 'タパスは美味しかったけど、少しお酒の種類が少なかったです。'); -- イタリアンバル (ID: 26)

-- 中華
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (22, 1, 27, 4, '台湾ラーメンの辛さが病みつきになります。汗だくで完食！'); -- 台湾ラーメン 味仙 (ID: 27)

-- 寿司/和食
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (23, 3, 29, 5, 'おでんの出汁が優しくて、寒い日に体が温まりました。'); -- おでん専門店 暖 (ID: 29)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (24, 2, 33, 5, '高級感があり、お寿司も会席も全てが素晴らしかったです。'); -- 寿司割烹 粋 (ID: 33)

-- イタリアン
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (25, 1, 34, 4, '家庭的な味で、ほっとするイタリアンでした。'); -- イタリア食堂 マンマ (ID: 34)

-- 中華
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (26, 4, 35, 4, '中華粥は優しく、朝食にぴったりでした。'); -- 中華粥 優 (ID: 35)

-- カフェ
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (27, 2, 36, 5, 'レトロな内装が可愛く、写真映えするカフェでした。'); -- カフェ レトロ レトロ (ID: 36)

-- カレー (ID: 46, 47, 48, 49, 50)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (28, 1, 46, 5, '日替わりカレーのスパイスが効いていて最高！'); -- spice spice (ID: 46)
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (29, 3, 48, 4, '欧風カレーのルーが濃厚で、とても食べ応えがありました。'); -- 欧風カレーといえば、ここ。 (ID: 48)

-- 寿司/和食
INSERT IGNORE INTO reviews (id, user_id, store_id, star_rating, comment)
VALUES (30, 4, 37, 4, '焼鳥と日本酒の相性が抜群で、落ち着いて飲めました。'); -- 焼鳥と日本酒 鶏次郎 (ID: 37)


-- #################################
-- 6. roles テーブル
-- #################################
INSERT IGNORE INTO roles (id, name) 
VALUES (1, 'ROLE_GENERAL');

INSERT IGNORE INTO roles (id, name) 
VALUES (2, 'ROLE_ADMIN');

-- #################################
-- 7. users テーブル
-- #################################

-- ID 1: 佐藤 太郎 (ROLE_GENERAL) - レビューデータで使用
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (1, '佐藤 太郎', 'サトウ タロウ', '460-0003', '愛知県名古屋市中区錦1-1-1', '090-1234-5678', 'taro.sato@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, true);

-- ID 2: 鈴木 花子 (ROLE_ADMIN) - レビューと予約データで使用
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (2, '鈴木 花子', 'スズキ ハナコ', '450-0002', '愛知県名古屋市中村区名駅4-27-1', '090-1234-5678', 'hanako.suzuki@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 2, true);

-- ID 3: 高橋 義勝 (ROLE_GENERAL) - 予約データで使用
INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (3, '高橋 義勝', 'タカハシ ヨシカツ', '444-0044', '愛知県岡崎市康生通西4-71', '090-1234-5678', 'yoshikatsu.takahashi@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, false);

INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (4, '田中 幸美', 'タナカ サチミ', '440-0897', '愛知県豊橋市松葉町1-11', '090-1234-5678', 'sachimi.tanaka@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, false);

INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (5, '伊藤 雅', 'イトウ ミヤビ', '471-0025', '愛知県豊田市西町6-85-1', '090-1234-5678', 'miyabi.ito@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, false);

INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (6, '渡辺 正保', 'ワタナベ マサヤス', '491-0858', '愛知県一宮市栄3-1-2', '090-1234-5678', 'masayasu.watanabe@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, false);

INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (7, '山本 真由美', 'ヤマモト マユミ', '485-0041', '愛知県小牧市中央1-1', '090-1234-5678', 'mayumi.yamamoto@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, false);

INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (8, '中村 安民', 'ナカムラ ヤスタミ', '448-0858', '愛知県刈谷市大手町1-1', '090-1234-5678', 'yasutami.nakamura@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, false);

INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (9, '小林 章緒', 'コバヤシ アキオ', '446-0032', '愛知県安城市御幸本町1-1', '090-1234-5678', 'akio.kobayashi@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, false);

INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (10, '加藤 祐子', 'カトウ ユウコ', '470-2200', '愛知県知多郡阿久比町', '090-1234-5678', 'yuko.kato@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, false);

INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (11, '吉田 秋美', 'ヨシダ アキミ', '480-1121', '愛知県長久手市山越901', '090-1234-5678', 'akimi.yoshida@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, false);

INSERT IGNORE INTO users (id, name, furigana, postal_code, address, phone_number, email, password, role_id, enabled) 
VALUES (12, '山田 信平', 'ヤマダ シンペイ', '464-0802', '愛知県名古屋市千種区星ヶ丘元町15', '090-1234-5678', 'shinpei.yamada@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, false);




-- #################################
-- 8. reservation テーブル (日時入り)
-- #################################

-- store_idと対応する予約時間 (店舗ID: 開店時間 -> 予約時間)
-- 1: 17:00:00 -> 19:30:00
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, number_of_people)
VALUES (1, 1, 1, '2025-12-15 19:30:00', 2);

-- 2: 18:00:00 -> 20:00:00
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, number_of_people)
VALUES (2, 1, 2, '2025-12-24 20:00:00', 4);

-- 3: 11:30:00 -> 18:30:00
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, number_of_people) 
VALUES (3, 1, 3, '2025-12-16 18:30:00', 3);

-- 4: 09:00:00 -> 11:00:00 
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, number_of_people) 
VALUES (4, 1, 4, '2025-12-25 11:00:00', 5);

-- 5: 11:00:00 -> 19:00:00
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, number_of_people) 
VALUES (5, 1, 5, '2025-12-17 19:00:00', 2);

-- 6: 17:30:00 -> 19:30:00
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, number_of_people) 
VALUES (6, 1, 6, '2025-12-26 19:30:00', 4);

-- 7: 11:00:00 -> 13:00:00 
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, number_of_people) 
VALUES (7, 1, 7, '2025-12-18 13:00:00', 3);

-- 8: 08:00:00 -> 09:30:00 
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, number_of_people) 
VALUES (8, 1, 8, '2025-12-27 09:30:00', 2);

-- 9: 17:00:00 -> 20:00:00
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, number_of_people) 
VALUES (9, 1, 9, '2025-12-19 20:00:00', 5);

-- 10: 17:00:00 -> 19:00:00
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, number_of_people) 
VALUES (10, 1, 10, '2025-12-28 19:00:00', 3);

-- 10: 17:00:00 -> 19:00:00
INSERT IGNORE INTO reservations (id, user_id, store_id, reservation_date, number_of_people) 
VALUES (11, 1, 11, '2025-12-30 17:30:00', 3);


-- #################################
-- 9. favoritesテーブル
-- #################################
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (1, 1, 1);
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (2, 2, 1);
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (3, 3, 1);
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (4, 4, 1);
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (5, 5, 1);
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (6, 6, 1);
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (7, 7, 1);
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (8, 8, 1);
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (9, 9, 1);
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (10, 10, 1);
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (11, 11, 1);
INSERT IGNORE INTO favorites (id, store_id, user_id) VALUES (12, 12, 1);