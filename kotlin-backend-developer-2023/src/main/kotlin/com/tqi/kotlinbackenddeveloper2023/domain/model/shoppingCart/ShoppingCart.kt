package com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart

import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class ShoppingCart(

    @Id
    var id: Long = 1L,

    @OneToMany(mappedBy = "shoppingCart")
    var listProductsPlacedInTheCart: MutableList<ProductsPlacedInTheCart>,

)
