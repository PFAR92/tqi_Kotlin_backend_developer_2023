package com.tqi.kotlinbackenddeveloper2023.domain.service.impl

import com.tqi.kotlinbackenddeveloper2023.domain.repository.product.ProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart.ProductsPlacedInTheCartRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart.ShoppingCartRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.shoppingCart.impl.ShoppingCartService
import org.mockito.Mockito.mock

class ShoppingCartServiceTest {

    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var productsPlacedInTheCartRepository: ProductsPlacedInTheCartRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var shoppingCartService: ShoppingCartService


    fun setUp() {
        shoppingCartRepository = mock(ShoppingCartRepository::class.java)
        productsPlacedInTheCartRepository = mock(ProductsPlacedInTheCartRepository::class.java)
        productRepository = mock(ProductRepository::class.java)
        shoppingCartService = ShoppingCartService(shoppingCartRepository, productsPlacedInTheCartRepository, productRepository)
    }


}