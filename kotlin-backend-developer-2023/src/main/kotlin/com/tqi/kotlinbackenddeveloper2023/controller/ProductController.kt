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
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("product")
@Tag(name = "Products", description = "Endpoints to manage inventory products")
class ProductController(private val productService: ProductService) {

    @Operation(summary = "add product", description = "endpoint to add a new product to stock")
    @PostMapping
    fun saveProduct(@RequestBody @Valid productDto: ProductDto): ResponseEntity<ProductView> {
        val savedProduct = productService.save(productDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductView(savedProduct))
    }

    @Operation(summary = "change product", description = "endpoint to change a product from stock")
    @PutMapping
    fun alterationProduct(@RequestBody @Valid productUpdateDto: ProductUpdateDto): ResponseEntity<ProductView> {
        val updatedProduct = productService.alteration(productUpdateDto.toEntity())
        return ResponseEntity.status(HttpStatus.OK).body(ProductView(updatedProduct))
    }

    @Operation(summary = "information of all products", description = "endpoint to see all registered products")
    @GetMapping
    fun findAllProducts(): ResponseEntity<List<ProductView>> {
        val listProduct = this.productService.findAll().stream()
            .map { product: Product -> ProductView(product) }
            .collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(listProduct)
    }

    @Operation(summary = "product information", description = "endpoint to fetch information for a specific product")
    @GetMapping("/{id}")
    fun findByIdProduct(@PathVariable id: Long): ResponseEntity<ProductView> {
        val findProduct = productService.findById(id)
        return ResponseEntity.status(HttpStatus.OK).body(ProductView(findProduct))
    }

    @Operation(summary = "delete product", description = "endpoint to delete a product from stock")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@PathVariable id: Long) {
        productService.delete(id)
    }

}