package com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale

import jakarta.persistence.*
import java.math.BigDecimal

@Entity
data class VoucherProduct(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val productName: String,
    val productQuantity: Int,
    val productUnitPrice: BigDecimal,
    val productValue: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "proofOfSale_id")
    var proofOfSale: ProofOfSale?
)
