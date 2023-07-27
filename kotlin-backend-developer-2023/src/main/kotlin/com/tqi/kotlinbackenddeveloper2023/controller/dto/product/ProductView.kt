package com.tqi.kotlinbackenddeveloper2023.controller.dto.product

import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import java.math.BigDecimal

data class ProductView(

    var id: Long,
    var name: String,
    var unitOfMeasure: Product.UnitOfMeasure,
    var unitPrice: String,
    var category: String
) {
    constructor(product: Product) : this(
        id = product.id,
        name = product.name,
        unitOfMeasure = product.unitOfMeasure,
        unitPrice = "R$${product.unitPrice}",
        category = product.category.name
    )
}
