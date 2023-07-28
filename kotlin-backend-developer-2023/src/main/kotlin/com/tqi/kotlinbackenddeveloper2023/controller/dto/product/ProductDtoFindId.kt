package com.tqi.kotlinbackenddeveloper2023.controller.dto.product

import com.tqi.kotlinbackenddeveloper2023.domain.model.category.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ProductDtoFindId(

    @field:NotNull(message = "id is mandatory")
    val id: Long
) {
    fun toEntity() = Product(
        id = this.id,
        name = "",
        unitOfMeasure = Product.UnitOfMeasure.UNIDADE,
        unitPrice = BigDecimal.ZERO,
        category = Category(name = "")
    )
}
