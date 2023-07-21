package com.tqi.kotlinbackenddeveloper2023.controller.dto.product

import com.tqi.kotlinbackenddeveloper2023.domain.model.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.UnitOfMeasure
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import java.math.BigDecimal

data class ProductDto(
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
        name = this.name,
        unitOfMeasure = UnitOfMeasure.valueOf(this.unitOfMeasure),
        unitPrice = this.unitPrice,
        category = Category(name = this.category)
    )
}
