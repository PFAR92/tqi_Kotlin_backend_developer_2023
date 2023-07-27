package com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart

import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class ProductsPlacedInTheCart(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    val id: Long = 0L,

    @ManyToOne
    @JoinColumn(name = "shoppingCart_id")
    var shoppingCart: ShoppingCart,

    @OneToOne
    @JoinColumn(name = "product_id")
    var product: Product,
    var quantity: Int,
    var price: BigDecimal
)
