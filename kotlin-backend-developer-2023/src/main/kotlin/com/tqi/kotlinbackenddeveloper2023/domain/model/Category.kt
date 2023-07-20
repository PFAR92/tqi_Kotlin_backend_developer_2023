package com.tqi.kotlinbackenddeveloper2023.domain.model

import jakarta.persistence.*

@Entity
@Embeddable
data class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    var name: String = ""

)
