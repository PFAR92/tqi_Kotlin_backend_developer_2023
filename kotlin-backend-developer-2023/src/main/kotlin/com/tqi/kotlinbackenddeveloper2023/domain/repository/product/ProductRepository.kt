package com.tqi.kotlinbackenddeveloper2023.domain.repository.product;

import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, Long> {

    @Query("SELECT COUNT(p) > 0 FROM Product p WHERE p.category.id = :categoryId")
    fun existsCategoryAssociatedWithTheProduct(categoryId: Long): Boolean

    fun existsByName(name: String): Boolean

    fun findByName(name: String): Optional<Product>


}