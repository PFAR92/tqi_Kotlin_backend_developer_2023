package com.tqi.kotlinbackenddeveloper2023.controller

import com.tqi.kotlinbackenddeveloper2023.controller.dto.product.ProductDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.product.ProductUpdateDto
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.service.impl.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("product")
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun saveProduct(@RequestBody @Valid productDto: ProductDto): ResponseEntity<Product> {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productDto.toEntity()))
    }

    @PutMapping
    fun alterationProduct(@RequestBody @Valid productUpdateDto: ProductUpdateDto): ResponseEntity<Product> {
        return ResponseEntity.status(HttpStatus.OK).body(productService.alteration(productUpdateDto.toEntity()))
    }

    @GetMapping
    fun findAllProducts(): ResponseEntity<List<Product>> {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findAll())
    }

    @GetMapping("/id")
    fun findByIdProduct(@RequestBody product: Product): ResponseEntity<Product> {
        return ResponseEntity.status(HttpStatus.OK).body(product.id?.let { productService.findById(it) })
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@RequestBody product: Product) {
        product.id?.let { productService.delete(it) }
    }

}