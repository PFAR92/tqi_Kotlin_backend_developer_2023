CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    unit_of_measure VARCHAR(20) NOT NULL,
    unit_price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category(id)
);