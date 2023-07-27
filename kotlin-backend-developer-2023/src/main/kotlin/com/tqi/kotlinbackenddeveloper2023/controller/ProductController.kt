package com.tqi.kotlinbackenddeveloper2023.controller

import com.tqi.kotlinbackenddeveloper2023.controller.dto.product.ProductDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.product.ProductUpdateDto
import com.tqi.kotlinbackenddeveloper2023.controller.dto.product.ProductView
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.service.product.impl.ProductService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("product")
class ProductController(private val productService: ProductService) {

    @PostMapping
    fun saveProduct(@RequestBody @Valid productDto: ProductDto): ResponseEntity<ProductView> {
        val savedProduct = productService.save(productDto.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(ProductView(savedProduct))
    }

    @PutMapping
    fun alterationProduct(@RequestBody @Valid productUpdateDto: ProductUpdateDto): ResponseEntity<ProductView> {
        val updatedProduct = productService.alteration(productUpdateDto.toEntity())
        return ResponseEntity.status(HttpStatus.OK).body(ProductView(updatedProduct))
    }

    @GetMapping
    fun findAllProducts(): ResponseEntity<List<ProductView>> {
        val listProduct = this.productService.findAll().stream()
            .map { product: Product -> ProductView(product) }
            .collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(listProduct)
    }

    @GetMapping("/id")
    fun findByIdProduct(@RequestBody product: Product): ResponseEntity<ProductView> {
        val findProduct = productService.findById(product.id)
        return ResponseEntity.status(HttpStatus.OK).body(ProductView(findProduct))
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteProduct(@RequestBody product: Product) {
        productService.delete(product)
    }

}