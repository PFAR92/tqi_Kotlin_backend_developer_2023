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