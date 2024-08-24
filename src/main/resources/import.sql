CREATE TABLE IF NOT EXISTS products
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    brand        VARCHAR(255),
    name         VARCHAR(255),
    size         INT,
    fit          ENUM ('NARROW', 'REGULAR', 'WIDE'),
    price        DECIMAL(10, 2),
    release_date DATE,
    description  TEXT
);

CREATE TABLE IF NOT EXISTS features
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    name       VARCHAR(255),
    value      VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE IF NOT EXISTS reviews
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    product_id  INT,
    feature_id  INT NULL,
    rating      TINYINT,
    description TEXT,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (feature_id) REFERENCES features (id)
);