package com.tqi.kotlinbackenddeveloper2023.domain.repository.sale;

import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.ProofOfSale
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProofOfSaleRepository : JpaRepository<ProofOfSale, Long> {
}