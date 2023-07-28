package com.tqi.kotlinbackenddeveloper2023.controller.dto.sale

import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.VoucherProduct
import java.math.BigDecimal

data class VoucherProductView(
    val name: String,
    val unitPrice: BigDecimal,
    val quantity: Int,
    val value: BigDecimal,
) {
    constructor(voucherProduct: VoucherProduct) : this(
        name = voucherProduct.productName,
        unitPrice = voucherProduct.productUnitPrice,
        quantity = voucherProduct.productQuantity,
        value = voucherProduct.productValue
    )
}
