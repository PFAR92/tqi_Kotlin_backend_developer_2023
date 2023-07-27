package com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart

import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ShoppingCart
import java.math.BigDecimal
import java.util.stream.Collectors

data class ShoppingCartView(
    var cartValue: String,
    var listProductsPlacedInTheCart: MutableList<ProductsPlacedInTheCartView>
) {
    constructor(shoppingCart: ShoppingCart) : this(
        cartValue = "R$${shoppingCart.cartValue}",
        listProductsPlacedInTheCart = shoppingCart.listProductsPlacedInTheCart.stream()
            .map { product: ProductsPlacedInTheCart ->
                ProductsPlacedInTheCartView(
                    product
                )
            }.collect(Collectors.toList())
    )
}
