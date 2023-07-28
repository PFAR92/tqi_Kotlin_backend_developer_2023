package com.tqi.kotlinbackenddeveloper2023.controller.dto.sale

import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.ProofOfSale
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDateTime

data class ProofOfSaleDtoFindId(
    @field:NotNull(message = "id is mandatory")
    val id: Long
) {
    fun toEntity() = ProofOfSale(
        id = this.id,
        dateTime = LocalDateTime.now(),
        value = BigDecimal.ZERO,
        paymentOptions = "",
        voucherProduct = mutableListOf()
    )
}
