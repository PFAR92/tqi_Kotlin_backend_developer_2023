package com.tqi.kotlinbackenddeveloper2023.domain.service

import com.tqi.kotlinbackenddeveloper2023.domain.model.Category
import com.tqi.kotlinbackenddeveloper2023.domain.repository.CategoryRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val productRepository: ProductRepository
) {

    fun save(category: Category): Category {
        return if (categoryRepository.existsByName(category.name)) {
            categoryRepository.findByName(category.name).get()
        } else {
            categoryRepository.save(category)
        }
    }

    fun delete(category: Category) {

        if (!productRepository.existsCategoryAssociatedWithTheProduct(category.id)) {
            categoryRepository.deleteById(category.id)
        }
    }
}