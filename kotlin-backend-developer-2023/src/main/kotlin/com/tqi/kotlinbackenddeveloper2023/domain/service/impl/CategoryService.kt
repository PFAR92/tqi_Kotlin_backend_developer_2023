package com.tqi.kotlinbackenddeveloper2023.domain.service.impl

import com.tqi.kotlinbackenddeveloper2023.domain.model.Category
import com.tqi.kotlinbackenddeveloper2023.domain.repository.CategoryRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.ICategoryService
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService(private val categoryRepository: CategoryRepository) : ICategoryService {

    fun save(category: Category): Category {
        return categoryRepository.findById(category.id).orElse(categoryRepository.save(category))
    }

    override fun alteration(category: Category): Category {
        val existingCategory = categoryRepository.findById(category.id).get()
        existingCategory.name = category.name
        return categoryRepository.save(existingCategory)
    }

    override fun findAll(): List<Category> {
        return categoryRepository.findAll()
    }

    override fun findByName(name: String): List<Category> {
        return categoryRepository.findByNameContaining(name)
    }

    override fun deleteByName(name: String) {
        return categoryRepository.deleteByName(name)
    }
}