package com.tqi.kotlinbackenddeveloper2023.controller.dto.sale

import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.PaymentOptions
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.Sale
import jakarta.validation.constraints.Pattern
import java.math.BigDecimal

data class SaleDto(
    @field:Pattern(
        regexp = "^(DINHEIRO|PIX|DEBITO|CREDITO)$",
        message = "fill options available (DINHEIRO, PIX, DEBITO, CREDITO)"
    )
    val paymentOptions: String
) {
    fun toEntity() = Sale(
        totalSaleAmount = BigDecimal.ZERO,
        paymentOptions = PaymentOptions.valueOf(this.paymentOptions)
    )
}
