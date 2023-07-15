package com.tqi.kotlinbackenddeveloper2023.domain.service.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.repository.ProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.IProductService

class ProductService(
    private val productRepository: ProductRepository,
    private val categoryService: CategoryService
    ): IProductService {

    override fun save(product: Product): Product {
        product.category = categoryService.save(product.category)
        return productRepository.save(product)
    }

    override fun alteration(product: Product): Product {
        val productAlteration = productRepository.findById(product.id).orElseThrow {
            throw BusinessException("id ${product.id} not found")
        }

        productAlteration.apply {
            category = categoryService.save(product.category)
            name = product.name
            unitOfMeasure = product.unitOfMeasure
            unitPrice = product.unitPrice
        }
        return productRepository.save(productAlteration)
    }

    override fun findAll(): List<Product> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Product {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        TODO("Not yet implemented")
    }
}