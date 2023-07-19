package com.tqi.kotlinbackenddeveloper2023.domain.service.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.Category
import com.tqi.kotlinbackenddeveloper2023.domain.repository.CategoryRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.ProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.ICategoryService
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) : ICategoryService {

    fun save(category: Category): Category {
        return if (categoryRepository.existsByName(category.name)) {
            categoryRepository.findByName(category.name).get()
        } else {
            categoryRepository.save(category)
        }
    }

    override fun alteration(category: Category): Category {
        val existingCategory = category.id?.let {
            categoryRepository.findById(it).orElseThrow {
                throw BusinessException("id ${category.id} not found")
            }
        }
        existingCategory?.name = category.name
        return existingCategory?.let { categoryRepository.save(it) } ?: throw BusinessException("Category not found")
    }

    override fun findAll(): List<Category> {
        return categoryRepository.findAll()
    }

    override fun findByName(name: String): List<Category> {
        return categoryRepository.findByNameContaining(name)
    }

    override fun deleteByName(name: String) {

        if (!productRepository.existsByCategoryName(name)) {
            if (categoryRepository.existsByName(name)) {
                categoryRepository.deleteByName(name)
            } else {
                throw BusinessException("name: $name not found")
            }

        } else {
            throw BusinessException("Cannot delete $name, there are products linked to it, please delete products first")
        }
    }
}