package com.tqi.kotlinbackenddeveloper2023.domain.repository;

import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository : JpaRepository<Product, Long> {
}