package com.tqi.kotlinbackenddeveloper2023.controller

import com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart.ShoppingCartDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart.ShoppingCartUpdateDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart.ShoppingCartView
import com.tqi.kotlinbackenddeveloper2023.domain.service.shoppingCart.impl.ShoppingCartService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("cart")
class ShoppingCartController(
    private val shoppingCartService: ShoppingCartService
) {

    @PostMapping
    fun addProductToCart(@RequestBody @Valid shoppingCart: ShoppingCartDto): ResponseEntity<ShoppingCartView> {
        val productInCart = shoppingCartService.addProductToCart(shoppingCart.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(ShoppingCartView(productInCart))
    }

    @PatchMapping
    fun changeTheQuantityOfTheProduct(@RequestBody @Valid shoppingCart: ShoppingCartUpdateDto): ResponseEntity<ShoppingCartView> {
        val updatedProduct = shoppingCartService.changeTheQuantityOfTheProduct(shoppingCart.toEntity())
        return ResponseEntity.status(HttpStatus.OK).body(ShoppingCartView(updatedProduct))
    }

    @GetMapping
    fun findShoppingCart(): ResponseEntity<ShoppingCartView> {
        return ResponseEntity.status(HttpStatus.OK).body(ShoppingCartView(shoppingCartService.findShoppingCart()))
    }
}