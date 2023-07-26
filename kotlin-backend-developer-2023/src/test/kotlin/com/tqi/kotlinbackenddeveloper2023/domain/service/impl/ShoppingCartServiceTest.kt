package com.tqi.kotlinbackenddeveloper2023.domain.service.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.category.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.UnitOfMeasure
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ShoppingCart
import com.tqi.kotlinbackenddeveloper2023.domain.repository.product.ProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart.ProductsPlacedInTheCartRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart.ShoppingCartRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.shoppingCart.impl.ShoppingCartService
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.util.*

class ShoppingCartServiceTest {

    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var productsPlacedInTheCartRepository: ProductsPlacedInTheCartRepository
    private lateinit var productRepository: ProductRepository
    private lateinit var shoppingCartService: ShoppingCartService


    @BeforeEach
    fun setUp() {
        shoppingCartRepository = mock(ShoppingCartRepository::class.java)
        productsPlacedInTheCartRepository = mock(ProductsPlacedInTheCartRepository::class.java)
        productRepository = mock(ProductRepository::class.java)
        shoppingCartService = ShoppingCartService(shoppingCartRepository, productsPlacedInTheCartRepository, productRepository)
    }

    @Test
    fun `save should throw exception when product does not exist`() {

        val productInTheCart = buildProductInCart()

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(buildShoppingCart()))
        `when`(productRepository.findByName(productInTheCart.product.name)).thenReturn(Optional.empty())

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            val productInTheCarSave = shoppingCartService.save(productInTheCart)
        }
        val expectedMessage = "The product entered does not exist, please check that it was entered correctly " +
                "or add the product to stock"
        Assertions.assertEquals(expectedMessage, exception.message)

        verify(productsPlacedInTheCartRepository, times(0)).save(productInTheCart)
        verify(shoppingCartRepository, times(0)).save(productInTheCart.shoppingCart)
    }

    @Test
    fun `saving a product must return the cart with the product inside, correct quantity and price`() {

        val productInTheCart = buildProductInCart()

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.empty())
        `when`(productsPlacedInTheCartRepository.save(productInTheCart)).thenReturn(productInTheCart)
        `when`(productRepository.findByName(productInTheCart.product.name)).thenReturn(Optional.of(buildProductInCart().product))
        `when`(shoppingCartRepository.save(any())).thenReturn(buildShoppingCart())

        val productInTheCarSave = shoppingCartService.save(productInTheCart)

        verify(productsPlacedInTheCartRepository, times(1)).save(productInTheCart)
        verify(shoppingCartRepository, times(1)).save(productInTheCart.shoppingCart)

    }

    @Test
    fun `saving a product that is already in the cart should update the quantity and price`() {

        val productInTheCart = buildProductInCart()
        val shoppingCart = buildShoppingCart(1L, mutableListOf(buildProductInCart()))

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(shoppingCart))
        `when`(productRepository.findByName(buildProductInCart().product.name)).thenReturn(Optional.of(buildProductInCart().product))
        `when`(shoppingCartRepository.save(any())).thenReturn(shoppingCart)


        val productInTheCarSave = shoppingCartService.save(productInTheCart)


        val updatedProduct = productInTheCarSave
            .listProductsPlacedInTheCart.first { it.product.id == productInTheCart.id }

        Assertions.assertEquals(updatedProduct.quantity, productInTheCart.quantity + productInTheCart.quantity)
        Assertions.assertEquals(updatedProduct.price, productInTheCart.price + productInTheCart.price)

        verify(productsPlacedInTheCartRepository, times(0)).save(productInTheCart)
    }


    private fun buildProductInCart (
        id: Long = 1L,
        shoppingCart: ShoppingCart = ShoppingCart(1L, mutableListOf<ProductsPlacedInTheCart>()),
        product: Product = Product(
            1L, "Coca Cola", UnitOfMeasure.UNIDADE, BigDecimal.valueOf(7.50), Category(1L, "Bebidas")
        ),
        quantity: Int = 2,
        price: BigDecimal = BigDecimal.valueOf(15.00)
    ) = ProductsPlacedInTheCart(
        id = id,
        shoppingCart = shoppingCart,
        product = product,
        quantity = quantity,
        price = price
    )

    private fun buildShoppingCart(
        id: Long = 1L,
        listProductsPlacedInTheCart: MutableList<ProductsPlacedInTheCart> = mutableListOf(),
    ) = ShoppingCart(
        id = id,
        listProductsPlacedInTheCart = listProductsPlacedInTheCart
    )


}