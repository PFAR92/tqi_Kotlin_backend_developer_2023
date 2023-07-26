package com.tqi.kotlinbackenddeveloper2023.domain.service.shoppingCart

import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ShoppingCart

interface IShoppingCart {

    fun save(product: ProductsPlacedInTheCart): ShoppingCart
    fun alteration(product: ProductsPlacedInTheCart): ShoppingCart
    fun deleteProduct(product: ProductsPlacedInTheCart): ShoppingCart
    fun findShoppingCart(): ShoppingCart
}