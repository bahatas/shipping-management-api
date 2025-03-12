CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    full_name VARCHAR(255),
    phone VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE password_reset_tokens (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE user_sessions (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    token VARCHAR(255) NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Dil desteği için
CREATE TABLE languages (
    id VARCHAR(5) PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    is_active BOOLEAN DEFAULT true
);

-- Çeviriler için
CREATE TABLE translations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    language_id VARCHAR(5) NOT NULL,
    translation_key VARCHAR(255) NOT NULL,
    translation_value TEXT NOT NULL,
    FOREIGN KEY (language_id) REFERENCES languages(id)
);

-- Siparişler tablosu
CREATE TABLE orders (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    order_number VARCHAR(50) NOT NULL UNIQUE,
    store_name VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    asin_code VARCHAR(50) NOT NULL,
    country VARCHAR(100) NOT NULL,
    order_amount DECIMAL(10,2) NOT NULL,
    additional_fee DECIMAL(10,2) DEFAULT 0,
    total_amount DECIMAL(10,2) NOT NULL,
    tracking_link TEXT,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    approved_at TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Ürün bilgileri tablosu
CREATE TABLE products (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    asin_code VARCHAR(50) NOT NULL,
    country VARCHAR(100) NOT NULL,
    product_name VARCHAR(255) NOT NULL,
    weight_lbs DECIMAL(10,2),
    dimensions_inch VARCHAR(50),
    postal_code VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Sipariş durumları için enum tablosu
CREATE TABLE order_statuses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    status_key VARCHAR(50) NOT NULL UNIQUE,
    status_name VARCHAR(100) NOT NULL,
    is_active BOOLEAN DEFAULT true
);

-- Adres bilgileri tablosu
CREATE TABLE shipping_addresses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    order_id BIGINT NOT NULL,
    country VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    address_line TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);

-- Örnek sipariş durumları
INSERT INTO order_statuses (status_key, status_name) VALUES
('pending', 'Onay Bekliyor'),
('approved', 'Onaylandı'),
('shipped', 'Gönderildi'),
('delivered', 'Teslim Edildi'),
('cancelled', 'İptal Edildi'),
('returned', 'İade Edildi');

-- İndeksler
CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_order_number ON orders(order_number);
CREATE INDEX idx_orders_asin_code ON orders(asin_code);
CREATE INDEX idx_products_asin_code ON products(asin_code);

INSERT INTO users (email, password_hash, full_name, phone, created_at, updated_at) 
VALUES ('test@example.com', 'hashedpassword', 'Test User', '5551234567', NOW(), NOW());