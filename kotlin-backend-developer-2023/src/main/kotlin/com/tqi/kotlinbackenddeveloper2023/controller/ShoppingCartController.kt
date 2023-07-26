package com.tqi.kotlinbackenddeveloper2023.controller

import com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart.ShoppingCartDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart.ShoppingCartUpdateDto
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ShoppingCart
import com.tqi.kotlinbackenddeveloper2023.domain.service.shoppingCart.impl.ShoppingCartService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("cart")
class ShoppingCartController(
    private val shoppingCartService: ShoppingCartService
) {

    @PostMapping
    fun addProductToCart(@RequestBody @Valid shoppingCart: ShoppingCartDto): ResponseEntity<ShoppingCart> {
        return ResponseEntity.status(HttpStatus.CREATED).body(shoppingCartService.addProductToCart(shoppingCart.toEntity()))
    }

    @PatchMapping
    fun changeTheQuantityOfTheProduct(@RequestBody @Valid shoppingCart: ShoppingCartUpdateDto): ResponseEntity<ShoppingCart> {
        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartService.changeTheQuantityOfTheProduct(shoppingCart.toEntity()))
    }

    @GetMapping
    fun findShoppingCart(): ResponseEntity<ShoppingCart> {
        return ResponseEntity.status(HttpStatus.OK).body(shoppingCartService.findShoppingCart())
    }
}