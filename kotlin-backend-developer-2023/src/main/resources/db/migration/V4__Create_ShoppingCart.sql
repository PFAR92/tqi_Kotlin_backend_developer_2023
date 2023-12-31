CREATE TABLE shopping_cart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    cart_value DECIMAL(10, 2) DEFAULT 0.00 NOT NULL
);

CREATE TABLE products_placed_in_the_cart (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    shopping_cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_shopping_cart FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(id) ON DELETE CASCADE,
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

