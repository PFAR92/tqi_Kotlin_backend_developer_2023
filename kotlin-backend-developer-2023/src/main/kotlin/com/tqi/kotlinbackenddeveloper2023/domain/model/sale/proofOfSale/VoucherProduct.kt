package com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale

import com.fasterxml.jackson.annotation.JsonIgnore
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.PaymentOptions
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.time.LocalDateTime

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
