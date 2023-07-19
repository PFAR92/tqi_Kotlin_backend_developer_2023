package com.tqi.kotlinbackenddeveloper2023.domain.repository;

import com.tqi.kotlinbackenddeveloper2023.domain.model.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface CategoryRepository : JpaRepository<Category, Long> {


    fun findByNameContaining(name: String): List<Category>

    @Transactional
    @Modifying
    @Query("DELETE FROM Category c WHERE c.name = :name")
    fun deleteByName(name: String)

    @Query("SELECT COUNT(c) > 0 FROM Category c WHERE c.name = :name")
    fun existsByName(name: String): Boolean


    fun findByName(name: String): Optional<Category>

}