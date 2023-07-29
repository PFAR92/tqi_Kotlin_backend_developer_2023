package com.tqi.kotlinbackenddeveloper2023.controller

import com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart.ShoppingCartDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart.ShoppingCartUpdateDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart.ShoppingCartView
import com.tqi.kotlinbackenddeveloper2023.domain.service.shoppingCart.impl.ShoppingCartService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("cart")
@Tag(name = "Shopping cart", description = "Endpoints to manage shopping cart products")
class ShoppingCartController(
    private val shoppingCartService: ShoppingCartService
) {

    @Operation(summary = "add a product to cart", description = "endpoint that adds products to existing shopping cart by product name")
    @PostMapping
    fun addProductToCart(@RequestBody @Valid shoppingCart: ShoppingCartDto): ResponseEntity<ShoppingCartView> {
        val productInCart = shoppingCartService.addProductToCart(shoppingCart.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(ShoppingCartView(productInCart))
    }

    @Operation(summary = "change the quantity of the product", description = "endpoint that changes the quantity of the product in the cart by the name of the added product")
    @PatchMapping
    fun changeTheQuantityOfTheProduct(@RequestBody @Valid shoppingCart: ShoppingCartUpdateDto): ResponseEntity<ShoppingCartView> {
        val updatedProduct = shoppingCartService.changeTheQuantityOfTheProduct(shoppingCart.toEntity())
        return ResponseEntity.status(HttpStatus.OK).body(ShoppingCartView(updatedProduct))
    }

    @Operation(summary = "cart info", description = "endpoint that shows current cart information and added products")
    @GetMapping
    fun findShoppingCart(): ResponseEntity<ShoppingCartView> {
        return ResponseEntity.status(HttpStatus.OK).body(ShoppingCartView(shoppingCartService.findShoppingCart()))
    }
}