package com.tqi.kotlinbackenddeveloper2023.controller.dto.sale

import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.ProofOfSale
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.VoucherProduct
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.stream.Collectors

data class CompletedSaleView(
    val sale: String,
    val products: MutableList<VoucherProductView>
)
{ constructor(proofOfSale: ProofOfSale) : this(
    sale = "sale successfully concluded at ${proofOfSale.dateTime.toLocalTime()} on ${proofOfSale.dateTime.toLocalDate()}, in the total amount " +
            "of R$${proofOfSale.value} with payment made in the modality ${proofOfSale.paymentOptions}",
    products = proofOfSale.voucherProduct.stream()
        .map { product: VoucherProduct -> VoucherProductView(product) }.collect(Collectors.toList())
)
}
