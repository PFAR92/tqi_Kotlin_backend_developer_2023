package com.tqi.kotlinbackenddeveloper2023.domain.service

import com.tqi.kotlinbackenddeveloper2023.domain.model.Category

interface ICategoryService {

    fun alteration(category: Category) : Category
    fun findAll(): List<Category>
    fun findByName(name: String) : List<Category>
    fun deleteByName(name: String)
}