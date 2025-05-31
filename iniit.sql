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


CREATE TABLE warehouses (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            warehouse_code VARCHAR(10) UNIQUE NOT NULL,
                            warehouse_name VARCHAR(100) NOT NULL,
                            country VARCHAR(50) NOT NULL,
                            state VARCHAR(50),
                            city VARCHAR(50) NOT NULL,
                            address_line TEXT NOT NULL,
                            postal_code VARCHAR(20),
                            phone VARCHAR(20),
                            email VARCHAR(100),
                            is_active BOOLEAN DEFAULT TRUE,
                            capacity_limit INT DEFAULT NULL,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE carriers (
                          id BIGINT PRIMARY KEY AUTO_INCREMENT,
                          carrier_code VARCHAR(20) UNIQUE NOT NULL,
                          carrier_name VARCHAR(100) NOT NULL,
                          logo_url VARCHAR(255),
                          tracking_url_template VARCHAR(255),
                          is_active BOOLEAN DEFAULT TRUE,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);


CREATE TABLE pricing_rules (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               warehouse_id BIGINT,
                               carrier_id BIGINT,
                               country_from VARCHAR(50) NOT NULL,
                               country_to VARCHAR(50) NOT NULL,
                               weight_min DECIMAL(8,2) DEFAULT 0,
                               weight_max DECIMAL(8,2),
                               base_price DECIMAL(10,2) NOT NULL,
                               price_per_kg DECIMAL(10,2) NOT NULL,
                               is_active BOOLEAN DEFAULT TRUE,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (warehouse_id) REFERENCES warehouses(id),
                               FOREIGN KEY (carrier_id) REFERENCES carriers(id)
);

CREATE TABLE user_balances (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               user_id BIGINT UNIQUE NOT NULL,
                               balance DECIMAL(12,2) DEFAULT 0.00,
                               currency VARCHAR(3) DEFAULT 'USD',
                               last_transaction_at TIMESTAMP NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE TABLE transactions (
                              id BIGINT PRIMARY KEY AUTO_INCREMENT,
                              user_id BIGINT NOT NULL,
                              order_id BIGINT NULL,
                              transaction_type ENUM('deposit', 'payment', 'refund', 'fee') NOT NULL,
                              amount DECIMAL(12,2) NOT NULL,
                              currency VARCHAR(3) DEFAULT 'USD',
                              payment_method ENUM('stripe', 'balance', 'bank_transfer') NOT NULL,
                              payment_reference VARCHAR(100),
                              status ENUM('pending', 'completed', 'failed', 'cancelled') DEFAULT 'pending',
                              description TEXT,
                              created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                              updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                              FOREIGN KEY (user_id) REFERENCES users(id),
                              FOREIGN KEY (order_id) REFERENCES orders(id)
);


CREATE TABLE package_tracking (
                                  id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                  order_id BIGINT NOT NULL,
                                  tracking_number VARCHAR(100) UNIQUE NOT NULL,
                                  carrier_id BIGINT NOT NULL,
                                  current_status VARCHAR(50) NOT NULL,
                                  current_location VARCHAR(100),
                                  estimated_delivery DATE,
                                  actual_delivery_date TIMESTAMP NULL,
                                  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                  FOREIGN KEY (order_id) REFERENCES orders(id),
                                  FOREIGN KEY (carrier_id) REFERENCES carriers(id)
);



CREATE TABLE tracking_events (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 tracking_id BIGINT NOT NULL,
                                 event_date TIMESTAMP NOT NULL,
                                 event_status VARCHAR(50) NOT NULL,
                                 event_location VARCHAR(100),
                                 event_description TEXT,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (tracking_id) REFERENCES package_tracking(id)
);


CREATE TABLE system_settings (
                                 id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                 setting_key VARCHAR(100) UNIQUE NOT NULL,
                                 setting_value TEXT,
                                 setting_type ENUM('string', 'number', 'boolean', 'json') DEFAULT 'string',
                                 description TEXT,
                                 is_public BOOLEAN DEFAULT FALSE,
                                 created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);



CREATE TABLE notifications (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               user_id BIGINT NOT NULL,
                               title VARCHAR(200) NOT NULL,
                               message TEXT NOT NULL,
                               type ENUM('info', 'success', 'warning', 'error') DEFAULT 'info',
                               is_read BOOLEAN DEFAULT FALSE,
                               related_order_id BIGINT NULL,
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                               FOREIGN KEY (user_id) REFERENCES users(id),
                               FOREIGN KEY (related_order_id) REFERENCES orders(id)
);




ALTER TABLE orders
    ADD COLUMN warehouse_id BIGINT AFTER user_id,
ADD COLUMN carrier_id BIGINT AFTER warehouse_id,
ADD COLUMN shipping_cost DECIMAL(10,2) DEFAULT 0.00 AFTER total_amount,
ADD COLUMN estimated_delivery_date DATE AFTER approved_at,
ADD FOREIGN KEY (warehouse_id) REFERENCES warehouses(id),
ADD FOREIGN KEY (carrier_id) REFERENCES carriers(id);




ALTER TABLE products
    ADD COLUMN category VARCHAR(100) AFTER product_name,
ADD COLUMN description TEXT AFTER category,
ADD COLUMN image_url VARCHAR(255) AFTER description;