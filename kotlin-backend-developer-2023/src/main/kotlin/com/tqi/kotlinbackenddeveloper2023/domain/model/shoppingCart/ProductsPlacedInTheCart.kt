package com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart

import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import jakarta.persistence.Embeddable
import jakarta.persistence.ManyToOne
import java.math.BigDecimal

@Embeddable
data class ProductsPlacedInTheCart(

    @ManyToOne
    val product: Product,
    val quantity: Int,
    val price: BigDecimal
)
