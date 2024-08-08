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

CREATE TABLE IF NOT EXISTS images
(
    id  INT PRIMARY KEY AUTO_INCREMENT,
    url VARCHAR(255),
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE IF NOT EXISTS features
(
    id   INT PRIMARY KEY,
    name VARCHAR(255),
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES products (id)
);

CREATE TABLE IF NOT EXISTS ratings
(
    id         INT PRIMARY KEY AUTO_INCREMENT,
    product_id INT,
    rating     INT,
    FOREIGN KEY (product_id) REFERENCES products (id)
);