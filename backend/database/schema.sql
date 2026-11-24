
-- ==========================================
-- UNIMART DATABASE SCHEMA
-- The University Commerce Network
-- ==========================================

-- Create Database
CREATE DATABASE IF NOT EXISTS unimart;
USE unimart;

-- ==========================================
-- 1. STUDENTS TABLE
-- ==========================================

CREATE TABLE IF NOT EXISTS students (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id VARCHAR(30) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    university VARCHAR(100) NOT NULL
);


-- ==========================================
-- 2. PRODUCTS TABLE
-- ==========================================

CREATE TABLE IF NOT EXISTS products (
    product_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50) NOT NULL,
    price DOUBLE NOT NULL,
    product_condition VARCHAR(50) NOT NULL,
    seller_id INT NOT NULL,
    available BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (seller_id)
        REFERENCES students(user_id),
    CHECK (price > 0)
);


-- ==========================================
-- 3. TRANSACTIONS TABLE
-- ==========================================

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,
    buyer_id INT NOT NULL,
    seller_id INT NOT NULL,
    product_id INT NOT NULL,
    amount DOUBLE NOT NULL,
    transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(30) DEFAULT 'Completed',
    FOREIGN KEY (buyer_id)
        REFERENCES students(user_id),
    FOREIGN KEY (seller_id)
        REFERENCES students(user_id),
    FOREIGN KEY (product_id)
        REFERENCES products(product_id),
    CHECK (amount > 0)
);


-- ==========================================
-- 4. GROUP PURCHASES TABLE
-- ==========================================

CREATE TABLE IF NOT EXISTS group_purchases (
    group_id INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT NOT NULL,
    minimum_participants INT NOT NULL,
    status VARCHAR(30) DEFAULT 'Active',
    FOREIGN KEY (product_id)
        REFERENCES products(product_id),
    CHECK (minimum_participants > 0)
);


-- ==========================================
-- 5. GROUP MEMBERS TABLE
-- ==========================================

CREATE TABLE IF NOT EXISTS group_members (
    group_id INT NOT NULL,
    student_id INT NOT NULL,
    PRIMARY KEY (group_id, student_id),
    FOREIGN KEY (group_id)
        REFERENCES group_purchases(group_id),
    FOREIGN KEY (student_id)
        REFERENCES students(user_id)
);


-- ==========================================
-- SCHEMA CREATION COMPLETED
-- ==========================================

SELECT 'UniMart database created successfully!'
AS Message;
