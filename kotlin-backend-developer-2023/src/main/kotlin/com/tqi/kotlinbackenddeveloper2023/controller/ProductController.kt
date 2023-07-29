package com.tqi.kotlinbackenddeveloper2023.controller

import com.tqi.kotlinbackenddeveloper2023.controller.dto.product.ProductDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.product.ProductUpdateDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.product.ProductView
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.service.product.impl.ProductService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("product")
@Tag(name = "Produtos", description = "Endpoints para gerenciar os produtos do estoque")
class ProductController(private val productService: ProductService) {

    @Operation(summary = "adicionar produto", description = "endpoint para cadastrar um novo produto ao estoque")
    @PostMapping
    fun saveProduct(@RequestBody @Valid productDto: ProductDto): ResponseEntity<ProductView> {
        val savedProduct = productService.save(productDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductView(savedProduct))
    }

    @Operation(summary = "alterar produto", description = "endpoint para alterar um produto do estoque")
    @PutMapping
    fun alterationProduct(@RequestBody @Valid productUpdateDto: ProductUpdateDto): ResponseEntity<ProductView> {
        val updatedProduct = productService.alteration(productUpdateDto.toEntity())
        return ResponseEntity.status(HttpStatus.OK).body(ProductView(updatedProduct))
    }

    @Operation(summary = "informações dos produtos", description = "endpoint para ver todos os produtos cadastrados")
    @GetMapping
    fun findAllProducts(): ResponseEntity<List<ProductView>> {
        val listProduct = this.productService.findAll().stream()
            .map { product: Product -> ProductView(product) }
            .collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(listProduct)
    }

    @Operation(summary = "informação de um produto", description = "endpoint para buscar informação de um produto específico")
    @GetMapping("/{id}")
    fun findByIdProduct(@PathVariable id: Long): ResponseEntity<ProductView> {
        val findProduct = productService.findById(id)
        return ResponseEntity.status(HttpStatus.OK).body(ProductView(findProduct))
    }

    @Operation(summary = "excluir produto", description = "endpoint para excluir um produto do estoque")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@PathVariable id: Long) {
        productService.delete(id)
    }

}