package com.tqi.kotlinbackenddeveloper2023.domain.service.shoppingCart

import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ShoppingCart

interface IShoppingCart {

    fun addProductToCart(product: ProductsPlacedInTheCart): ShoppingCart
    fun changeTheQuantityOfTheProduct(product: ProductsPlacedInTheCart): ShoppingCart
    fun findShoppingCart(): ShoppingCart
}