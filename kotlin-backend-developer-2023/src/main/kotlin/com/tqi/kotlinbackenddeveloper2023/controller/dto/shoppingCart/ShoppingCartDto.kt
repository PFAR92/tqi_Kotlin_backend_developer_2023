package com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart

import com.tqi.kotlinbackenddeveloper2023.domain.model.category.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ShoppingCart
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class ShoppingCartDto(

    @field:NotBlank(message = "this field is mandatory")
    val product: String = "",

    @field:Min(value = 1, message = "the minimum quantity entered is 1")
    @field:NotNull(message = "this field is mandatory")
    val quantity: Int = 1
) {
    fun toEntity() = ProductsPlacedInTheCart(
        product = Product(
            name = this.product,
            unitOfMeasure = Product.UnitOfMeasure.UNIDADE,
            unitPrice = BigDecimal.ZERO,
            category = Category(name = "")
        ),
        quantity = this.quantity,
        price = BigDecimal.ZERO,
        shoppingCart = ShoppingCart(1L, BigDecimal.ZERO, mutableListOf())
    )

}