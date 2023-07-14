package com.tqi.kotlinbackenddeveloper2023.controller

import com.tqi.kotlinbackenddeveloper2023.domain.model.Category
import com.tqi.kotlinbackenddeveloper2023.domain.service.impl.CategoryService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/category")
class CategoryController(private val categoryService: CategoryService) {

    @PutMapping
    fun alterationCategory(@RequestBody @Valid category: Category): ResponseEntity<Category> {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.alteration(category))
    }

    @GetMapping("/name")
    fun findCategoryByName(@RequestBody category: Category): ResponseEntity<List<Category>> {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findByName(category.name))
    }

    @GetMapping
    fun findCategories(): ResponseEntity<List<Category>> {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll())
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCategory(@RequestBody @Valid category: Category) {
        return categoryService.deleteByName(category.name)
    }


}