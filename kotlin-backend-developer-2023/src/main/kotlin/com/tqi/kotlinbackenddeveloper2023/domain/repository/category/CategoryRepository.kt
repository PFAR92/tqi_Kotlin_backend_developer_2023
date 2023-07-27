package com.tqi.kotlinbackenddeveloper2023.domain.repository.category;

import com.tqi.kotlinbackenddeveloper2023.domain.model.category.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {


    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.name = :name")
    fun existsByName(name: String): Boolean

    fun findByName(name: String): Optional<Category>

}