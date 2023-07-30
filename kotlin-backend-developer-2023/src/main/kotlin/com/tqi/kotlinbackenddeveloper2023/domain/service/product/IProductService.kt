package com.tqi.kotlinbackenddeveloper2023.domain.service.product

import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product

interface IProductService {

    fun save(product: Product): Product
    fun alteration(product: Product): Product
    fun findAll(): List<Product>
    fun findById(id: Long): Product
    fun delete(id: Long)
}