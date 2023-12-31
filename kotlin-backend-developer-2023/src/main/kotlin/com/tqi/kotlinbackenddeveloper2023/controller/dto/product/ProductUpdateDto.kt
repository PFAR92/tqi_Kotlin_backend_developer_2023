package com.tqi.kotlinbackenddeveloper2023.controller.dto.product

import com.tqi.kotlinbackenddeveloper2023.domain.model.category.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import jakarta.validation.constraints.*
import java.math.BigDecimal

data class ProductUpdateDto(
    @field:NotNull
    var id: Long = 0L,

    @field:NotBlank(message = "this field is mandatory")
    var name: String = "",

    @field:Pattern(
        regexp = "^(UNIDADE|QUILOGRAMA|LITRO|CAIXA)$",
        message = "fill options available (UNIDADE, QUILOGRAMA, LITRO, CAIXA)"
    )
    var unitOfMeasure: String = "",

    @field:DecimalMin(value = "0", inclusive = false, message = "Price cannot be zero or less")
    @field:Digits(integer = 5, fraction = 2, message = "Enter a valid numeric value with up to two decimal places")
    var unitPrice: BigDecimal = BigDecimal.ZERO,

    @field:NotBlank(message = "this field is mandatory")
    var category: String = ""
) {
    fun toEntity() = Product(
        id = this.id,
        name = this.name,
        unitOfMeasure = Product.UnitOfMeasure.valueOf(this.unitOfMeasure),
        unitPrice = this.unitPrice,
        category = Category(name = this.category)
    )
}
