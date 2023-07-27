package com.tqi.kotlinbackenddeveloper2023.controller.dto.product

import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.UnitOfMeasure
import java.math.BigDecimal

data class ProductView(

    var name: String,
    var unitOfMeasure: UnitOfMeasure,
    var unitPrice: BigDecimal,
    var category: String
) {
    constructor(product: Product) : this(
        name = product.name,
        unitOfMeasure = product.unitOfMeasure,
        unitPrice = product.unitPrice,
        category = product.category.name
    )
}
