package com.tqi.kotlinbackenddeveloper2023.repository;

import com.tqi.kotlinbackenddeveloper2023.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {


    fun findByNameContaining(name: String): List<Category>

    fun deleteByName(name: String)
}