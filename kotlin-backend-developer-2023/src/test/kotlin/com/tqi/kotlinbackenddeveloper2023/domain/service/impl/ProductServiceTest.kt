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