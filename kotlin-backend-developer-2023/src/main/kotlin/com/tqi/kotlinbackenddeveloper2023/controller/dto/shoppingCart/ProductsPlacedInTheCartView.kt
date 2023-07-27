package com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart

import com.tqi.kotlinbackenddeveloper2023.controller.dto.product.ProductView
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import java.math.BigDecimal

data class ProductsPlacedInTheCartView(

    var product: ProductView,
    var quantity: Int,
    var price: BigDecimal
) {
    constructor(productsPlacedInTheCart: ProductsPlacedInTheCart) : this(
        product = ProductView(productsPlacedInTheCart.product),
        quantity = productsPlacedInTheCart.quantity,
        price = productsPlacedInTheCart.price
    )
}
