package com.tqi.kotlinbackenddeveloper2023.domain.service.sale

import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.Sale
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.ProofOfSale

interface ISaleService {

    fun finalizeSale(sale: Sale): ProofOfSale
    fun showReceipts(): List<ProofOfSale>
    fun findVoucherById(id: Long): ProofOfSale
    fun deleteReceiptById(id: Long)
}