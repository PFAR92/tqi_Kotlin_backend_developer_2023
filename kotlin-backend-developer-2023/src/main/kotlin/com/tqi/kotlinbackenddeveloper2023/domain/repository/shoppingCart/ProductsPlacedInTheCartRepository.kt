package com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart;

import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductsPlacedInTheCartRepository : JpaRepository<ProductsPlacedInTheCart, Long> {
}