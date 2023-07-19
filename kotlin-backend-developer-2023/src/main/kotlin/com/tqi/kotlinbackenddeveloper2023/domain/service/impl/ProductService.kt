package com.tqi.kotlinbackenddeveloper2023.domain.service.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.repository.ProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.IProductService
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

        val productAlteration = product.id?.let {
            productRepository.findById(it).orElseThrow {
                throw BusinessException("id ${product.id} not found")
            }
        }

        productAlteration?.apply {

            if (productRepository.existsByName(product.name)) {
                val productCanBeChanged: Product = productRepository.findByName(product.name)
                if (productCanBeChanged.id != product.id) {
                    throw BusinessException("there is already a product with name ${product.name} registered, impossible to have a duplicate product")
                }
            } else {
                category = categoryService.save(product.category)
                name = product.name
                unitOfMeasure = product.unitOfMeasure
                unitPrice = product.unitPrice
            }
        }
        return productAlteration?.let { productRepository.save(it) } ?: throw BusinessException("Product not found")
    }

    override fun findAll(): List<Product> {
        return productRepository.findAll()
    }

    override fun findById(id: Long): Product {
        return productRepository.findById(id).orElseThrow {
            BusinessException("id $id not found")
        }
    }

    override fun delete(id: Long) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id)
        } else {
            throw BusinessException("id $id not found")
        }
    }
}