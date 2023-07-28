package com.tqi.kotlinbackenddeveloper2023.domain.service.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.category.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ShoppingCart
import com.tqi.kotlinbackenddeveloper2023.domain.repository.product.ProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart.ProductsPlacedInTheCartRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart.ShoppingCartRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.shoppingCart.impl.ShoppingCartService
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
        shoppingCartService =
            ShoppingCartService(shoppingCartRepository, productsPlacedInTheCartRepository, productRepository)
    }

    @Test
    fun `save should throw exception when product does not exist`() {

        val productInTheCart = buildProductInCart()

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(buildShoppingCart()))
        `when`(productRepository.findByName(productInTheCart.product.name)).thenReturn(Optional.empty())

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            shoppingCartService.addProductToCart(productInTheCart)
        }
        val expectedMessage = "The product entered does not exist, please check that it was entered correctly " +
                "or add the product to stock"
        Assertions.assertEquals(expectedMessage, exception.message)
    }

    @Test
    fun `saving a product must return the cart with the product inside, correct quantity and price`() {

        val productInTheCart = buildProductInCart()

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.empty())
        `when`(productsPlacedInTheCartRepository.save(productInTheCart)).thenReturn(productInTheCart)
        `when`(productRepository.findByName(productInTheCart.product.name)).thenReturn(Optional.of(buildProductInCart().product))
        `when`(shoppingCartRepository.save(any())).thenReturn(buildShoppingCart())

        val productInTheCarSave = shoppingCartService.addProductToCart(productInTheCart)

        var cartValues = BigDecimal.ZERO
        for (product in productInTheCarSave.listProductsPlacedInTheCart) {
            cartValues += product.price
        }

        Assertions.assertEquals(productInTheCarSave.cartValue, cartValues)

        verify(productsPlacedInTheCartRepository, times(1)).save(productInTheCart)
        verify(shoppingCartRepository, times(1)).save(productInTheCart.shoppingCart)

    }

    @Test
    fun `saving a product that is already in the cart should update the quantity and price`() {

        val productInTheCart = buildProductInCart()
        val shoppingCart = buildShoppingCart(1L, BigDecimal.ZERO, mutableListOf(buildProductInCart()))

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(shoppingCart))
        `when`(productRepository.findByName(buildProductInCart().product.name)).thenReturn(
            Optional.of(
                buildProductInCart().product
            )
        )
        `when`(shoppingCartRepository.save(any())).thenReturn(shoppingCart)


        val productInTheCarSave = shoppingCartService.addProductToCart(productInTheCart)


        val updatedProduct = productInTheCarSave
            .listProductsPlacedInTheCart.first { it.product.id == productInTheCart.id }

        Assertions.assertEquals(updatedProduct.quantity, productInTheCart.quantity + productInTheCart.quantity)
        Assertions.assertEquals(updatedProduct.price, productInTheCart.price + productInTheCart.price)

        verify(productsPlacedInTheCartRepository, times(1)).save(any())
    }

    @Test
    fun `Exception is thrown when trying to update product from an empty cart`() {

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            shoppingCartService.changeTheQuantityOfTheProduct(buildProductInCart())
        }

        val expectedMessage = "Cart is empty, there are no products to update"

        Assertions.assertEquals(expectedMessage, exception.message)

    }

    @Test
    fun `Exception is thrown when trying to update a product that is not in the cart`() {

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(buildShoppingCart()))

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            shoppingCartService.changeTheQuantityOfTheProduct(buildProductInCart())
        }

        val expectedMessage = "There is no such product in the cart, please add it first before changing the quantity"

        Assertions.assertEquals(expectedMessage, exception.message)


    }

    @Test
    fun `the product quantity is changed when requested and the price is updated`() {

        val updateProduct = buildProductInCart(quantity = 5)
        val shoppingCart = buildShoppingCart(1L, BigDecimal.ZERO, mutableListOf(buildProductInCart()))

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(shoppingCart))
        `when`(shoppingCartRepository.save(any())).thenReturn(shoppingCart)

        val updateShoppingCart = shoppingCartService.changeTheQuantityOfTheProduct(updateProduct)
        println(updateShoppingCart.toString())

        val updatedProduct = updateShoppingCart
            .listProductsPlacedInTheCart.first { it.product.id == updateProduct.id }

        Assertions.assertEquals(updatedProduct.quantity, 5)
        Assertions.assertEquals(updatedProduct.price, BigDecimal.valueOf(37.5))

        verify(productsPlacedInTheCartRepository, times(1)).save(any())
        verify(shoppingCartRepository, times(1)).save(any())
    }

    @Test
    fun `if the quantity is zero, the product is removed from the cart`() {

        val updateProduct = buildProductInCart(quantity = 0)
        val shoppingCart = buildShoppingCart(1L, BigDecimal.ZERO, mutableListOf(buildProductInCart()))

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(shoppingCart))
        `when`(productRepository.findByName(buildProductInCart().product.name)).thenReturn(
            Optional.of(
                buildProductInCart().product
            )
        )
        `when`(shoppingCartRepository.save(any())).thenReturn(shoppingCart)

        val updatedShoppingCart = shoppingCartService.changeTheQuantityOfTheProduct(updateProduct)

        Assertions.assertEquals(updatedShoppingCart.listProductsPlacedInTheCart.isEmpty(), true)
        verify(productsPlacedInTheCartRepository, times(1)).deleteById(updateProduct.id)
    }

    @Test
    fun `findShoppingCart should throw exception if cart not yet created`() {

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            shoppingCartService.findShoppingCart()
        }

        val expectedMessage = "Cart is empty, add products to view shopping cart"

        Assertions.assertEquals(expectedMessage, exception.message)
    }

    @Test
    fun `findShoppingCart should return the corresponding cart`() {

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(buildShoppingCart()))

        val shoppingCart = shoppingCartService.findShoppingCart()

        Assertions.assertEquals(shoppingCart, buildShoppingCart())

        verify(shoppingCartRepository, times(1)).findById(1L)
    }


    private fun buildProductInCart(
        id: Long = 1L,
        shoppingCart: ShoppingCart = ShoppingCart(1L, BigDecimal.ZERO, mutableListOf<ProductsPlacedInTheCart>()),
        product: Product = Product(
            1L, "Coca Cola", Product.UnitOfMeasure.UNIDADE, BigDecimal.valueOf(7.50), Category(1L, "Bebidas")
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
        cartValues: BigDecimal = BigDecimal.ZERO,
        listProductsPlacedInTheCart: MutableList<ProductsPlacedInTheCart> = mutableListOf(),
    ) = ShoppingCart(
        id = id,
        cartValue = cartValues,
        listProductsPlacedInTheCart = listProductsPlacedInTheCart
    )


}