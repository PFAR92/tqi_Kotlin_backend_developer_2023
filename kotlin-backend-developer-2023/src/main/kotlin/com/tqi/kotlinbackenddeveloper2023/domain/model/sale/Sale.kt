package com.tqi.kotlinbackenddeveloper2023.domain.model.sale

import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.math.BigDecimal

data class Sale(

    var totalSaleAmount: BigDecimal,

    @field:Enumerated(EnumType.STRING)
    val paymentOptions: PaymentOptions
)

enum class PaymentOptions {
    DINHEIRO,
    PIX,
    DEBITO,
    CREDITO
}
