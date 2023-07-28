package com.tqi.kotlinbackenddeveloper2023.domain.model.product

import com.tqi.kotlinbackenddeveloper2023.domain.model.category.Category
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class Product(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    var name: String,

    @field:Enumerated(EnumType.STRING)
    var unitOfMeasure: UnitOfMeasure,

    var unitPrice: BigDecimal,

    @ManyToOne
    @Embedded
    var category: Category
) {

    enum class UnitOfMeasure {
        UNIDADE,
        QUILOGRAMA,
        LITRO,
        CAIXA
    }
}
