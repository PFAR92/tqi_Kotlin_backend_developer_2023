package com.tqi.kotlinbackenddeveloper2023.controller

import com.tqi.kotlinbackenddeveloper2023.controller.dto.sale.CompletedSaleView
import com.tqi.kotlinbackenddeveloper2023.controller.dto.sale.ProofOfSaleDtoFindId
import com.tqi.kotlinbackenddeveloper2023.controller.dto.sale.ProofOfSaleView
import com.tqi.kotlinbackenddeveloper2023.controller.dto.sale.SaleDto
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.ProofOfSale
import com.tqi.kotlinbackenddeveloper2023.domain.service.sale.impl.SaleService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("sale")
@Tag(name = "Finalize Sale", description = "Endpoints to finalize the sale and manage sales receipts")
class SaleController(
    private val saleService: SaleService
) {

    @Operation(summary = "finalize the sale", description = "after passing the payment method, this endpoint completes the sale, generates a confirmation message and a receipt")
    @PostMapping
    fun finalizeSale(@RequestBody @Valid sale: SaleDto): ResponseEntity<CompletedSaleView> {
        val finalizeSale = saleService.finalizeSale(sale.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CompletedSaleView(finalizeSale))
    }

    @Operation(summary = "information on all sales made", description = "endpoint that shows all sales receipts made")
    @GetMapping
    fun showReceipts(): ResponseEntity<List<ProofOfSaleView>> {
        val listProofOfSale = saleService.showReceipts().stream()
            .map { proofOfSale: ProofOfSale -> ProofOfSaleView(proofOfSale) }.collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(listProofOfSale)
    }

    @Operation(summary = "voucher information", description = "endpoint that shows a specific voucher")
    @GetMapping("/{id}")
    fun findVoucherById(@PathVariable id: Long): ResponseEntity<ProofOfSaleView> {
        val voucher = saleService.findVoucherById(id)
        return ResponseEntity.status(HttpStatus.OK).body(ProofOfSaleView(voucher))
    }

    @Operation(summary = "delete proof of sale", description = "endpoint to delete a sales receipt")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReceiptById(@PathVariable id: Long) {
        saleService.deleteReceiptById(id)
    }
}