package com.tqi.kotlinbackenddeveloper2023.domain.service.impl


import com.tqi.kotlinbackenddeveloper2023.domain.model.category.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.UnitOfMeasure
import com.tqi.kotlinbackenddeveloper2023.domain.repository.CategoryRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.ProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.CategoryService
import com.tqi.kotlinbackenddeveloper2023.domain.service.product.impl.ProductService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.util.*


internal class CategoryServiceTest {

    private lateinit var categoryRepository: CategoryRepository
    private lateinit var categoryService: CategoryService
    private lateinit var productRepository: ProductRepository
    private lateinit var productService: ProductService

    @BeforeEach
    fun setUp() {
        categoryRepository = mock(CategoryRepository::class.java)
        productRepository = mock(ProductRepository::class.java)
        categoryService = CategoryService(categoryRepository, productRepository)
        productService = ProductService(productRepository, categoryService)
    }

    @Test
    fun `save should return saved category`() {

        val category = Category(1L, "Test Category")
        `when`(categoryRepository.existsByName(category.name)).thenReturn(false)
        `when`(categoryRepository.save(category)).thenReturn(category)

        val savedCategory = categoryService.save(category)

        Assertions.assertEquals(category, savedCategory)
        verify(categoryRepository, times(1)).existsByName(category.name)
        verify(categoryRepository, times(0)).findById(1L)
        verify(categoryRepository, times(1)).save(category)

    }

    @Test
    fun `save should return existing category`() {
        val category = Category(1L, "Test Category")

        `when`(categoryRepository.existsByName(category.name)).thenReturn(true)
        `when`(categoryRepository.findByName("Test Category")).thenReturn(Optional.of(category))

        val savedCategory = categoryService.save(category)

        Assertions.assertEquals(category, savedCategory)

        verify(categoryRepository, times(1)).existsByName(category.name)
        verify(categoryRepository, times(1)).findByName("Test Category")
        verify(categoryRepository, times(0)).save(category)
    }


    @Test
    fun `delete should delete the category when found and not associated with a product`() {

        `when`(productRepository.existsCategoryAssociatedWithTheProduct(buildProduct().category.id)).thenReturn(false)

        categoryService.delete(buildProduct().category)

        verify(productRepository, times(1)).existsCategoryAssociatedWithTheProduct(buildProduct().id)
        verify(categoryRepository, times(1)).deleteById(buildProduct().category.id)
    }

    @Test
    fun `delete must not delete a category that is associated with a product`() {

        `when`(productRepository.existsCategoryAssociatedWithTheProduct(buildProduct().category.id)).thenReturn(true)

        categoryService.delete(buildProduct().category)

        verify(productRepository, times(1)).existsCategoryAssociatedWithTheProduct(buildProduct().category.id)
        verify(categoryRepository, times(0)).deleteById(buildProduct().category.id)
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