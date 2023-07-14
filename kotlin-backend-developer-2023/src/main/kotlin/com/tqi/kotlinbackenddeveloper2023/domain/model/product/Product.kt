package com.tqi.kotlinbackenddeveloper2023.domain.model.product

import com.tqi.kotlinbackenddeveloper2023.domain.model.Category
import jakarta.persistence.*
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Digits
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,

    @ManyToOne
    @Embedded
    var category: Category,

    @field:NotBlank
    var name: String,

    @field:Enumerated(EnumType.STRING)
    var unitOfMeasure: UnitOfMeasure,

    @field:DecimalMin(value = "0", inclusive = false)
    @field:Digits(integer = 5, fraction = 2)
    var UnitPrice: BigDecimal
)
