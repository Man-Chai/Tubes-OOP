-- 1. Buat database
CREATE DATABASE IF NOT EXISTS topup_game;
USE topup_game;

-- 2. Tabel users
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    katasandi VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 3. Tabel games
CREATE TABLE games (
    game_id INT AUTO_INCREMENT PRIMARY KEY,
    game_name VARCHAR(100) NOT NULL,
    publisher VARCHAR(100),
    description TEXT
);

-- 4. Tabel payment_methods
CREATE TABLE payment_methods (
    method_id INT AUTO_INCREMENT PRIMARY KEY,
    method_name VARCHAR(50) NOT NULL,
    provider VARCHAR(50)
);

-- 5. Tabel topup_orders
CREATE TABLE topup_orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    game_id INT NOT NULL,
    method_id INT NOT NULL,
    game_user_id VARCHAR(100) NOT NULL, -- ID pengguna di dalam game
    amount DECIMAL(10,2) NOT NULL, -- nominal top-up
    status ENUM('pending', 'completed', 'failed') DEFAULT 'pending',
    order_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (game_id) REFERENCES games(game_id),
    FOREIGN KEY (method_id) REFERENCES payment_methods(method_id)
);
