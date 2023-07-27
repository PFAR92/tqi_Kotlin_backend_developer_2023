package com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
data class ProofOfSale(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,
    val dateTime: LocalDateTime,
    val value: BigDecimal,
    val paymentOptions: String,

    @OneToMany(mappedBy = "proofOfSale", cascade = [CascadeType.ALL], orphanRemoval = true)
    val voucherProduct: MutableList<VoucherProduct>
)


