package com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.math.BigDecimal

@Entity
data class ShoppingCart(

    @Id
    var id: Long = 1L,

    var cartValue: BigDecimal,

    @OneToMany(mappedBy = "shoppingCart")
    var listProductsPlacedInTheCart: MutableList<ProductsPlacedInTheCart>,
)
