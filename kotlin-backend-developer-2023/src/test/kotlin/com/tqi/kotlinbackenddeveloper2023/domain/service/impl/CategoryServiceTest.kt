package com.tqi.kotlinbackenddeveloper2023.domain.service.impl


import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.Category
import com.tqi.kotlinbackenddeveloper2023.domain.repository.CategoryRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*


internal class CategoryServiceTest {

    private lateinit var categoryRepository: CategoryRepository
    private lateinit var categoryService: CategoryService

    @BeforeEach
    fun setUp() {
        categoryRepository = mock(CategoryRepository::class.java)
        categoryService = CategoryService(categoryRepository)
    }

    @Test
    fun `save should return saved category`() {

        val category = Category(1L, "Test Category")
        `when`(categoryRepository.findById(category.id)).thenReturn(Optional.empty())
        `when`(categoryRepository.save(category)).thenReturn(category)

        val savedCategory = categoryService.save(category)

        Assertions.assertEquals(category, savedCategory)
        verify(categoryRepository, times(1)).findById(category.id)
        verify(categoryRepository, times(1)).save(category)

    }

    @Test
    fun `save should return existing category`() {
        val category = Category(1L, "Test Category")
        `when`(categoryRepository.findById(category.id)).thenReturn(Optional.of(category))

        val savedCategory = categoryService.save(category)

        Assertions.assertEquals(category, savedCategory)
        verify(categoryRepository, times(1)).findById(category.id)
        verify(categoryRepository, times(0)).save(category)
    }

    @Test
    fun `alteration should return updated category`() {
        val existingCategory = Category(1L, "Existing Category")
        val updatedCategory = Category(1L, "Updated Category")
        `when`(categoryRepository.findById(existingCategory.id)).thenReturn(Optional.of(existingCategory))
        `when`(categoryRepository.save(existingCategory)).thenReturn(updatedCategory)

        val result = categoryService.alteration(updatedCategory)

        Assertions.assertEquals(updatedCategory, result)
        Assertions.assertEquals(updatedCategory.name, existingCategory.name)
        verify(categoryRepository, times(1)).findById(existingCategory.id)
        verify(categoryRepository, times(1)).save(existingCategory)
    }

    @Test
    fun `alteration should throw exception when category not found`() {
        val category = Category(1L, "Non-existing Category")
        `when`(categoryRepository.findById(category.id)).thenReturn(Optional.empty())

        Assertions.assertThrows(BusinessException::class.java) {
            categoryService.alteration(category)
        }

        verify(categoryRepository, times(1)).findById(category.id)
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
        `when`(categoryRepository.existsByName(categoryName)).thenReturn(true)

        categoryService.deleteByName(categoryName)

        verify(categoryRepository, times(1)).existsByName(categoryName)
        verify(categoryRepository, times(1)).deleteByName(categoryName)
    }

    @Test
    fun `deleteByName should throw exception when category not found`() {
        val categoryName = "Non-existing Category"
        `when`(categoryRepository.existsByName(categoryName)).thenReturn(false)

        Assertions.assertThrows(BusinessException::class.java) {
            categoryService.deleteByName(categoryName)
        }

        verify(categoryRepository, times(1)).existsByName(categoryName)
        verify(categoryRepository, times(0)).deleteByName(categoryName)
    }
}