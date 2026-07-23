-- MySQL 8+ manual schema for MicroServiceDemo

CREATE DATABASE IF NOT EXISTS AuthDb
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS ProductDb
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

CREATE DATABASE IF NOT EXISTS OrderDb
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

-- =====================
-- AuthDb
-- =====================
USE AuthDb;

CREATE TABLE IF NOT EXISTS users (
  id BIGINT NOT NULL AUTO_INCREMENT,
  username VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  full_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_users_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Optional seed user for login test (password is plain text in current demo)
INSERT INTO users (username, password, full_name)
SELECT 'alice', '123456', 'Alice Zhang'
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'alice');

-- =====================
-- ProductDb
-- =====================
USE ProductDb;

CREATE TABLE IF NOT EXISTS products (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) NULL,
  price DOUBLE NOT NULL,
  stock INT NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Optional seed data used by current demo
INSERT INTO products (name, description, price, stock)
SELECT 'Laptop', '14 inch lightweight laptop', 4999.00, 30
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Laptop');

INSERT INTO products (name, description, price, stock)
SELECT 'Keyboard', 'Mechanical keyboard', 399.00, 120
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Keyboard');

INSERT INTO products (name, description, price, stock)
SELECT 'Headphones', 'Noise cancelling headphones', 899.00, 65
WHERE NOT EXISTS (SELECT 1 FROM products WHERE name = 'Headphones');

-- =====================
-- OrderDb
-- =====================
USE OrderDb;

CREATE TABLE IF NOT EXISTS orders (
  id BIGINT NOT NULL AUTO_INCREMENT,
  invoice_number VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  total_amount DECIMAL(18,2) NOT NULL,
  created_at DATETIME(6) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY uk_orders_invoice_number (invoice_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE IF NOT EXISTS order_items (
  id BIGINT NOT NULL AUTO_INCREMENT,
  order_id BIGINT NOT NULL,
  product_id BIGINT NOT NULL,
  product_name VARCHAR(255) NOT NULL,
  quantity INT NOT NULL,
  unit_price DECIMAL(18,2) NOT NULL,
  sub_total DECIMAL(18,2) NOT NULL,
  PRIMARY KEY (id),
  KEY idx_order_items_order_id (order_id),
  CONSTRAINT fk_order_items_order
    FOREIGN KEY (order_id) REFERENCES orders(id)
    ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Optional seed order for invoice query test
INSERT INTO orders (invoice_number, username, total_amount, created_at)
SELECT 'INV-DEMO-00000001', 'alice', 5398.00, NOW(6)
WHERE NOT EXISTS (SELECT 1 FROM orders WHERE invoice_number = 'INV-DEMO-00000001');

INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, sub_total)
SELECT o.id, 1, 'Laptop', 1, 4999.00, 4999.00
FROM orders o
WHERE o.invoice_number = 'INV-DEMO-00000001'
  AND NOT EXISTS (
    SELECT 1 FROM order_items oi
    WHERE oi.order_id = o.id AND oi.product_id = 1
  );

INSERT INTO order_items (order_id, product_id, product_name, quantity, unit_price, sub_total)
SELECT o.id, 2, 'Keyboard', 1, 399.00, 399.00
FROM orders o
WHERE o.invoice_number = 'INV-DEMO-00000001'
  AND NOT EXISTS (
    SELECT 1 FROM order_items oi
    WHERE oi.order_id = o.id AND oi.product_id = 2
  );
