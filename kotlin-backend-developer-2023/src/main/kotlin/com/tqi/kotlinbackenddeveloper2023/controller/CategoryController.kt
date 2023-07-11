package com.tqi.kotlinbackenddeveloper2023.controller

import com.tqi.kotlinbackenddeveloper2023.model.Category
import com.tqi.kotlinbackenddeveloper2023.service.impl.CategoryService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/category")
class CategoryController(private val categoryService: CategoryService) {

    @PostMapping
    fun alterationCategory(@RequestBody category: Category) : ResponseEntity<Category> {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.alteration(category))
    }

    @GetMapping("/name")
    fun findCategoryByName(@RequestBody category: Category) : ResponseEntity<List<Category>> {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findByName(category.name))
    }

    @GetMapping
    fun findCategories () : ResponseEntity<List<Category>> {
        return ResponseEntity.status(HttpStatus.OK).body(categoryService.findAll())
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteCategory (@RequestBody category: Category) {
        return categoryService.deleteByName(category.name)
    }




}