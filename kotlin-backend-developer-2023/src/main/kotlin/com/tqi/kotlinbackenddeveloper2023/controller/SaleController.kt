package com.tqi.kotlinbackenddeveloper2023.controller

import com.tqi.kotlinbackenddeveloper2023.controller.dto.sale.CompletedSaleView
import com.tqi.kotlinbackenddeveloper2023.controller.dto.sale.ProofOfSaleDtoFindId
import com.tqi.kotlinbackenddeveloper2023.controller.dto.sale.ProofOfSaleView
import com.tqi.kotlinbackenddeveloper2023.controller.dto.sale.SaleDto
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.ProofOfSale
import com.tqi.kotlinbackenddeveloper2023.domain.service.sale.impl.SaleService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("sale")
class SaleController(
    private val saleService: SaleService
) {

    @PostMapping
    fun finalizeSale(@RequestBody @Valid sale: SaleDto): ResponseEntity<CompletedSaleView> {
        val sale = saleService.finalizeSale(sale.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CompletedSaleView(sale))
    }

    @GetMapping
    fun showReceipts(): ResponseEntity<List<ProofOfSaleView>> {
        val listProofOfSale = saleService.showReceipts().stream()
            .map { proofOfSale: ProofOfSale -> ProofOfSaleView(proofOfSale) }.collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(listProofOfSale)
    }

    @GetMapping("id")
    fun findVoucherById(@RequestBody @Valid proofOfSale: ProofOfSaleDtoFindId): ResponseEntity<ProofOfSaleView> {
        val voucher = saleService.findVoucherById(proofOfSale.id)
        return ResponseEntity.status(HttpStatus.OK).body(ProofOfSaleView(voucher))
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReceiptById(@RequestBody @Valid proofOfSale: ProofOfSaleDtoFindId) {
        saleService.deleteReceiptById(proofOfSale.id)
    }
}