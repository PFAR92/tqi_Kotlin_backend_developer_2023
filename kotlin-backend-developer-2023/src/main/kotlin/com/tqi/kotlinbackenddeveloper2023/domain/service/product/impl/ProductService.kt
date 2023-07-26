package com.tqi.kotlinbackenddeveloper2023.domain.service.product.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.category.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.repository.product.ProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.CategoryService
import com.tqi.kotlinbackenddeveloper2023.domain.service.product.IProductService
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productRepository: ProductRepository,
    private val categoryService: CategoryService
) : IProductService {

    override fun save(product: Product): Product {
        if (!productRepository.existsByName(product.name)) {
            product.category = categoryService.save(product.category)
            return productRepository.save(product)
        } else {
            throw BusinessException("Product ${product.name} already exists, cannot save an existing product")
        }
    }

    override fun alteration(product: Product): Product {

        val categoryThatMayBeExcluded: Category

        val productAlteration = productRepository.findById(product.id).orElseThrow {
                throw BusinessException("id ${product.id} not found")
            }

        productAlteration.apply {
            categoryThatMayBeExcluded = productAlteration.category

            if (productRepository.existsByName(product.name)) {
                val productCanBeChanged: Product = productRepository.findByName(product.name).get()
                if (productCanBeChanged.id != product.id) {
                    throw BusinessException("there is already a product with name ${product.name} registered, impossible to have a duplicate product")
                }
            }

            category = categoryService.save(product.category)
            name = product.name
            unitOfMeasure = product.unitOfMeasure
            unitPrice = product.unitPrice
        }
        productRepository.save(productAlteration)
        categoryService.delete(categoryThatMayBeExcluded)

        return productAlteration
    }

    override fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    override fun findById(id: Long): Product {
        return productRepository.findById(id).orElseThrow {
            BusinessException("id $id not found")
        }
    }

    override fun delete(product: Product) {
        if (productRepository.existsById(product.id)) {
            val deleteProduct = productRepository.findById(product.id)
            productRepository.delete(deleteProduct.get())
            categoryService.delete(deleteProduct.get().category)

        } else {
            throw BusinessException("id ${product.id} not found")
        }
    }
}