package com.tqi.kotlinbackenddeveloper2023.domain.service.impl


import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.UnitOfMeasure
import com.tqi.kotlinbackenddeveloper2023.domain.repository.CategoryRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.ProductRepository
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
    fun `alteration should return updated category`() {
        val existingCategory = Category(1L, "Existing Category")
        val updatedCategory = Category(1L, "Updated Category")
        `when`(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory))
        `when`(categoryRepository.save(updatedCategory)).thenReturn(updatedCategory)

        val result = categoryService.alteration(updatedCategory)

        Assertions.assertEquals(updatedCategory, result)

        verify(categoryRepository, times(1)).findById(1L)
        verify(categoryRepository, times(1)).save(existingCategory)
    }

    @Test
    fun `alteration should throw exception when category not found`() {
        val category = Category(1L, "Non-existing Category")
        `when`(categoryRepository.findById(1L)).thenReturn(Optional.empty())

        Assertions.assertThrows(BusinessException::class.java) {
            categoryService.alteration(category)
        }

        verify(categoryRepository, times(1)).findById(1L)
        verify(categoryRepository, times(0)).save(category)
    }

    @Test
    fun `findAll should return all categories`() {
        val categories = listOf(
            Category(1L, "Category 1"), Category(2L, "Category 2"), Category(3L, "Category 3")
        )
        `when`(categoryRepository.findAll()).thenReturn(categories)

        val result = categoryService.findAll()

        Assertions.assertEquals(categories, result)
        verify(categoryRepository, times(1)).findAll()
    }

    @Test
    fun `findByName should return matching categories`() {
        val searchName = "Category"
        val categories = listOf(
            Category(1L, "Category 1"), Category(2L, "Category 2"), Category(3L, "Category 3")
        )
        `when`(categoryRepository.findByNameContaining(searchName)).thenReturn(categories)

        val result = categoryService.findByName(searchName)

        Assertions.assertEquals(categories, result)
        verify(categoryRepository, times(1)).findByNameContaining(searchName)
    }

    @Test
    fun `deleteByName should delete category when found`() {
        val categoryName = "Category"
        `when`(productRepository.existsByCategoryName(categoryName)).thenReturn(false)
        `when`(categoryRepository.existsByName(categoryName)).thenReturn(true)

        categoryService.deleteByName(categoryName)

        verify(productRepository, times(1)).existsByCategoryName(categoryName)
        verify(categoryRepository, times(1)).existsByName(categoryName)
        verify(categoryRepository, times(1)).deleteByName(categoryName)
    }

    @Test
    fun `deleteByName should throw exception when category not found`() {
        val categoryName = "Non-existing Category"

        `when`(productRepository.existsByCategoryName(categoryName)).thenReturn(false)
        `when`(categoryRepository.existsByName(categoryName)).thenReturn(false)

        Assertions.assertThrows(BusinessException::class.java) {
            categoryService.deleteByName(categoryName)
        }

        verify(productRepository, times(1)).existsByCategoryName(categoryName)
        verify(categoryRepository, times(1)).existsByName(categoryName)
        verify(categoryRepository, times(0)).deleteByName(categoryName)
    }

    @Test
    fun `deleteByName should throw exception when category is associated with a product`() {


        val category = Category(1L, "Bebidas")
        val product = Product(
            1L, "Coca Cola", UnitOfMeasure.UNIDADE, BigDecimal.valueOf(7.50), category
        )

        `when`(productRepository.existsByCategoryName(category.name)).thenReturn(true)

        Assertions.assertThrows(BusinessException::class.java) {
            categoryService.deleteByName(category.name)
        }

        verify(productRepository, times(1)).existsByCategoryName(category.name)
        verify(categoryRepository, times(0)).deleteByName(category.name)

    }
}