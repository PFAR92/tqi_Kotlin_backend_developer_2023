package com.tqi.kotlinbackenddeveloper2023.controller.dto.sale

import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.ProofOfSale
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.VoucherProduct
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.stream.Collectors

data class ProofOfSaleView(
    val id: Long,
    val dateTime: LocalDateTime,
    val saleValue: BigDecimal,
    val paymentOptions: String,
    val products: MutableList<VoucherProductView>
) {
    constructor(proofOfSale: ProofOfSale) : this(
    id = proofOfSale.id,
    dateTime = proofOfSale.dateTime,
    saleValue = proofOfSale.value,
    paymentOptions = proofOfSale.paymentOptions,
    products = proofOfSale.voucherProduct.stream()
        .map { product: VoucherProduct -> VoucherProductView(product) }.collect(Collectors.toList())
)
}
