package com.tqi.kotlinbackenddeveloper2023.domain.repository.sale;

import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.VoucherProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface VoucherProductRepository : JpaRepository<VoucherProduct, Long> {
}