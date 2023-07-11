package com.tqi.kotlinbackenddeveloper2023.service

import com.tqi.kotlinbackenddeveloper2023.model.Category

interface ICategoryService {

    fun alteration(category: Category) : Category
    fun findAll(): List<Category>
    fun findByName(name: String) : List<Category>
    fun deleteByName(name: String)
}