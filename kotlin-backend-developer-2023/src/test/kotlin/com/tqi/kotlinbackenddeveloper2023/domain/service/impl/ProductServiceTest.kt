package com.tqi.kotlinbackenddeveloper2023.domain.service.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.UnitOfMeasure
import com.tqi.kotlinbackenddeveloper2023.domain.repository.ProductRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.util.*

class ProductServiceTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var productService: ProductService
    private lateinit var categoryService: CategoryService

    @BeforeEach
    fun setUp() {
        productRepository = mock(ProductRepository::class.java)
        categoryService = mock(CategoryService::class.java)
        productService = ProductService(productRepository, categoryService)
    }

    @Test
    fun `save must return saved product`() {

        `when`(categoryService.save(buildProduct().category)).thenReturn(buildProduct().category)
        `when`(productRepository.save(any())).thenReturn(buildProduct())

        val savedProduct = productRepository.save(buildProduct())

        Assertions.assertEquals(buildProduct(), savedProduct)
        verify(productRepository, times(1)).save(buildProduct())
    }

    @Test
    fun `change should throw exception when product is not found`() {

        `when`(productRepository.findById(buildProduct().id)).thenReturn(Optional.empty())

        Assertions.assertThrows(BusinessException::class.java) {
            productService.alteration(buildProduct())
        }

        verify(productRepository, times(1)).findById(buildProduct().id)
        verify(productRepository, times(0)).save(buildProduct())
    }

    @Test
    fun `alteration should return updated product`() {

        val updatedProduct = buildProduct().copy(unitOfMeasure = UnitOfMeasure.CAIXA,
            unitPrice = BigDecimal.valueOf(21))

        `when`(productRepository.findById(updatedProduct.id)).thenReturn(Optional.of(buildProduct()))
        `when`(categoryService.save(updatedProduct.category)).thenReturn(updatedProduct.category)
        `when`(productRepository.save(updatedProduct)).thenReturn(updatedProduct)

        val productAlteration = productService.alteration(updatedProduct)

        Assertions.assertEquals(updatedProduct, productAlteration)

        verify(productRepository, times(1)).findById(updatedProduct.id)
        verify(productRepository, times(1)).save(updatedProduct)

    }

    @Test
    fun `findAll should return all products`() {

        val product1 = buildProduct()
        val product2 = buildProduct().copy(id = 2, name = "Pepsi Cola")
        val product3 = buildProduct().copy(id = 3, name = "Guaraná Antártica")
        val products = listOf(product1, product2, product3)

        `when`(productRepository.findAll()).thenReturn(products)

        val result = productService.findAll()

        Assertions.assertEquals(products, result)

        verify(productRepository, times(1)).findAll()
    }

    @Test
    fun `findById should throw exception when category not found`() {

        `when`(productRepository.findById(any())).thenReturn(Optional.empty())

        Assertions.assertThrows(BusinessException::class.java) {
            productService.findById(buildProduct().id)
        }

        verify(productRepository, times(1)).findById(buildProduct().id)
    }

    @Test
    fun `findById should return the corresponding product`() {

        `when`(productRepository.findById(buildProduct().id)).thenReturn(Optional.of(buildProduct()))

        val existsProduct = productService.findById(buildProduct().id)

        Assertions.assertEquals(existsProduct, buildProduct())

        verify(productRepository, times(1)).findById(buildProduct().id)
    }

    @Test
    fun `deleteById should throw exception when product not found`() {

        `when`(productRepository.existsById(buildProduct().id)).thenReturn(false)

        Assertions.assertThrows(BusinessException::class.java) {
            productService.delete(buildProduct().id)
        }

        verify(productRepository, times(1)).existsById(buildProduct().id)
        verify(productRepository, times(0)).deleteById(buildProduct().id)
    }

    @Test
    fun `deleteById should delete product when found`() {

        `when`(productRepository.existsById(buildProduct().id)).thenReturn(true)

        productService.delete(buildProduct().id)

        verify(productRepository, times(1)).existsById(buildProduct().id)
        verify(productRepository, times(1)).deleteById(buildProduct().id)
    }


    private fun buildProduct(
        id: Long = 1L,
        category: Category = Category(
            1L,
            "BEBIDAS",
        ),
        name: String = "Coca Cola",
        unitOfMeasure: UnitOfMeasure = UnitOfMeasure.UNIDADE,
        unitPrice: BigDecimal = BigDecimal.valueOf(7.50)
    ) = Product(
        id = id,
        category = category,
        name = name,
        unitOfMeasure = unitOfMeasure,
        unitPrice = unitPrice
    )

}