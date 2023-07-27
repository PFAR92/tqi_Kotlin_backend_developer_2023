CREATE TABLE proof_of_sale (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    date_time TIMESTAMP NOT NULL,
    value DECIMAL(10, 2) NOT NULL,
    payment_options VARCHAR(255) NOT NULL
);

CREATE TABLE voucher_product (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_name VARCHAR(255) NOT NULL,
    product_quantity INT NOT NULL,
    product_unit_price DECIMAL(10, 2) NOT NULL,
    product_value DECIMAL(10, 2) NOT NULL,
    proof_of_sale_id BIGINT NOT NULL,
    FOREIGN KEY (proof_of_sale_id) REFERENCES proof_of_sale(id)
);