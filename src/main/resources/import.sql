/**
  {
  "shoe": {
    "id": 12345,
    "brand": "Nike",
    "model": "Air Max 270",
    "category": "Running",
    "color": "Black/White",
    "size": 10,
    "gender": "Unisex",
    "price": 150.00,
    "currency": "USD",
    "description": "The Nike Air Max 270 combines the exaggerated tongue from the Air Max 180 and classic elements from the Air Max 93. Featuring a Max Air unit that delivers all-day comfort, this shoe ensures a stylish and comfortable run.",
    "stock": 35,
    "releaseDate": "2023-01-15",
    "features": {
      "upperMaterial": "Mesh",
      "midsole": "Foam",
      "outsole": "Rubber",
      "technology": [
        "Air Max",
        "Flyknit",
        "React Foam"
      ]
    },
    "images": [
      "https://example.com/images/shoe1.jpg",
      "https://example.com/images/shoe2.jpg",
      "https://example.com/images/shoe3.jpg"
    ],
    "ratings": {
      "averageRating": 4.5,
      "numberOfReviews": 120
    },
    "availability": "In Stock"
  }
}
 */

CREATE TABLE IF NOT EXISTS features
(
    id    INT PRIMARY KEY,
    name  VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS images (
                                      id INT PRIMARY KEY,
                                      url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS products
(
    id           INT PRIMARY KEY,
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
    features     INT,
    images       INT,
    availability VARCHAR(255),
    FOREIGN KEY (features) REFERENCES features (id),
    FOREIGN KEY (images) REFERENCES images (id)
);

CREATE TABLE IF NOT EXISTS ratings
(
    id         INT PRIMARY KEY,
    product_id INT,
    rating     INT,
    FOREIGN KEY (product_id) REFERENCES products (id)
);