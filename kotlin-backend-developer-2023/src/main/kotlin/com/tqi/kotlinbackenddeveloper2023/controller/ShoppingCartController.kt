package com.tqi.kotlinbackenddeveloper2023.controller

import com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart.ShoppingCartDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart.ShoppingCartUpdateDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.shoppingCart.ShoppingCartView
import com.tqi.kotlinbackenddeveloper2023.domain.service.shoppingCart.impl.ShoppingCartService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("cart")
@Tag(name = "Carrinho de compras", description = "Endpoints para gerenciar os produtos do carrinho de compras")
class ShoppingCartController(
    private val shoppingCartService: ShoppingCartService
) {

    @Operation(summary = "adiciona um produto ao carrinho", description = "endpoint que vai adicionando os produtos no carrinho de compras existente pelo nome do produto")
    @PostMapping
    fun addProductToCart(@RequestBody @Valid shoppingCart: ShoppingCartDto): ResponseEntity<ShoppingCartView> {
        val productInCart = shoppingCartService.addProductToCart(shoppingCart.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(ShoppingCartView(productInCart))
    }

    @Operation(summary = "altera a quantidade do produto", description = "endpoint que altera a quantidade do produto no carrinho pelo nome do produto adicionado")
    @PatchMapping
    fun changeTheQuantityOfTheProduct(@RequestBody @Valid shoppingCart: ShoppingCartUpdateDto): ResponseEntity<ShoppingCartView> {
        val updatedProduct = shoppingCartService.changeTheQuantityOfTheProduct(shoppingCart.toEntity())
        return ResponseEntity.status(HttpStatus.OK).body(ShoppingCartView(updatedProduct))
    }

    @Operation(summary = "informações do carrinho", description = "endpoint que mostra as informações atuais do carrinho e os produtos adicionados")
    @GetMapping
    fun findShoppingCart(): ResponseEntity<ShoppingCartView> {
        return ResponseEntity.status(HttpStatus.OK).body(ShoppingCartView(shoppingCartService.findShoppingCart()))
    }
}