package com.tqi.kotlinbackenddeveloper2023.domain.model

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank

@Entity
@Embeddable
data class Category(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @field:NotBlank
    var name: String

)
