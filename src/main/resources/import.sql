CREATE TABLE IF NOT EXISTS products
(
    id           INT PRIMARY KEY AUTO_INCREMENT,
    brand        VARCHAR(255),
    model        VARCHAR(255),
    category     VARCHAR(255),
    color        VARCHAR(255),
    size         INT,
    gender       VARCHAR(255),
    price        DECIMAL(10, 2),
    description  TEXT,
    stock        INT,
    release_date DATE,
    availability VARCHAR(255)
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
    rating      INT,
    description TEXT,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (feature_id) REFERENCES features (id)
);