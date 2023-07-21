package com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart

import jakarta.persistence.*
import java.math.BigDecimal
import java.util.Arrays

@Entity
data class ShoppingCart(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @ElementCollection
    @CollectionTable(name = "products_placed_in_the_cart", joinColumns = [JoinColumn(name = "cart_id")])
    val products: List<ProductsPlacedInTheCart>,
    val cartValue: BigDecimal

)
