package com.tqi.kotlinbackenddeveloper2023.domain.service.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.category.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.UnitOfMeasure
import com.tqi.kotlinbackenddeveloper2023.domain.repository.ProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.CategoryService
import com.tqi.kotlinbackenddeveloper2023.domain.service.product.impl.ProductService
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

        val productToSave = buildProduct(id = 1L)

        `when`(productRepository.existsByName(productToSave.name)).thenReturn(false)
        `when`(categoryService.save(productToSave.category)).thenReturn(productToSave.category)
        `when`(productRepository.save(productToSave)).thenReturn(productToSave)

        val savedProduct = productService.save(productToSave)

        Assertions.assertEquals(productToSave, savedProduct)

        verify(productRepository, times(1)).existsByName(productToSave.name)
        verify(productRepository, times(1)).save(productToSave)
    }

    @Test
    fun `save should throw an exception when the product already exists`() {

        val productToSave = buildProduct(id = 1L)
        `when`(productRepository.existsByName(productToSave.name)).thenReturn(true)

        val  exception = Assertions.assertThrows(BusinessException::class.java) {
            productService.save(productToSave)
        }
        val expectedMessage = "Product ${productToSave.name} already exists, cannot save an existing product"

        Assertions.assertEquals(expectedMessage, exception.message)
        verify(productRepository, times(1)).existsByName(productToSave.name)
        verify(productRepository, times(0)).save(productToSave)
    }

    @Test
    fun `change should throw exception when product is not found`() {


        `when`(productRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            productService.alteration(buildProduct())
        }
        val expectedMessage = "id ${buildProduct().id} not found"

        Assertions.assertEquals(expectedMessage, exception.message)
        verify(productRepository, times(1)).findById(1L)
        verify(productRepository, times(0)).save(buildProduct())
    }

    @Test
    fun `alteration should return updated product`() {

        val updatedProduct = buildProduct().copy(
            unitOfMeasure = UnitOfMeasure.CAIXA,
            unitPrice = BigDecimal.valueOf(21)
        )

        `when`(productRepository.existsByName(buildProduct().name)).thenReturn(false)
        `when`(productRepository.findById(1L)).thenReturn(Optional.of(buildProduct()))
        `when`(categoryService.save(updatedProduct.category)).thenReturn(updatedProduct.category)
        `when`(productRepository.save(updatedProduct)).thenReturn(updatedProduct)

        val productAlteration = productService.alteration(updatedProduct)

        Assertions.assertEquals(updatedProduct, productAlteration)

        verify(productRepository, times(1)).existsByName(buildProduct().name)
        verify(productRepository, times(1)).findById(1L)
        verify(productRepository, times(1)).save(updatedProduct)

    }

    @Test
    fun `trying to duplicate an existing product throws exception`() {
        val duplicateProduct = buildProduct().copy(
            id = 2L,
        )
        `when`(productRepository.findById(2L)).thenReturn(Optional.of(duplicateProduct))
        `when`(productRepository.existsByName(duplicateProduct.name)).thenReturn(true)
        `when`(productRepository.findByName(duplicateProduct.name)).thenReturn(buildProduct())

       val exception = Assertions.assertThrows(BusinessException::class.java) {
            val productAlteration = productService.alteration(duplicateProduct)
        }

        val expectedMessage = "there is already a product with name ${duplicateProduct.name} registered, " +
                "impossible to have a duplicate product"

        Assertions.assertEquals(expectedMessage, expectedMessage)
        verify(productRepository, times(1)).findById(2L)
        verify(productRepository, times(0)).save(duplicateProduct)

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

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            productService.findById(1L)
        }
        val expectedMessage = "id 1 not found"

        Assertions.assertEquals(expectedMessage, exception.message)
        verify(productRepository, times(1)).findById(1L)
    }

    @Test
    fun `findById should return the corresponding product`() {

        `when`(productRepository.findById(1L)).thenReturn(Optional.of(buildProduct()))

        val existsProduct = productService.findById(1L)

        Assertions.assertEquals(existsProduct, buildProduct())

        verify(productRepository, times(1)).findById(1L)
    }

    @Test
    fun `deleteById should throw exception when product not found`() {

        `when`(productRepository.existsById(1L)).thenReturn(false)

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            productService.delete(buildProduct())
        }
        val expectedMessage = "id 1 not found"

        Assertions.assertEquals(expectedMessage, exception.message)
        verify(productRepository, times(1)).existsById(1L)
        verify(productRepository, times(0)).deleteById(1L)
    }

    @Test
    fun `deleteById should delete product when found`() {

        `when`(productRepository.existsById(1L)).thenReturn(true)
        `when`(productRepository.findById(1L)).thenReturn(Optional.of(buildProduct()))

        productService.delete(buildProduct())

        verify(productRepository, times(1)).existsById(1L)
        verify(productRepository, times(1)).findById(1L)
        verify(productRepository, times(1)).delete(buildProduct())
    }


    private fun buildProduct(
        id: Long = 1L,
        name: String = "Coca Cola",
        unitOfMeasure: UnitOfMeasure = UnitOfMeasure.UNIDADE,
        unitPrice: BigDecimal = BigDecimal.valueOf(7.50),
        category: Category = Category(
            1L,
            "BEBIDAS",
        )
    ) = Product(
        id = id,
        name = name,
        unitOfMeasure = unitOfMeasure,
        unitPrice = unitPrice,
        category = category,
    )

}