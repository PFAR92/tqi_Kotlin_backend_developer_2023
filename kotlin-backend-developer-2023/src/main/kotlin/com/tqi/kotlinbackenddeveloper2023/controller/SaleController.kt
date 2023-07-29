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
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors

@RestController
@RequestMapping("sale")
@Tag(name = "Finalizar Venda", description = "Endpoints para finalizar a venda e gerenciar os comprovantes de venda")
class SaleController(
    private val saleService: SaleService
) {

    @Operation(summary = "finalizar a venda", description = "após passar o método de pagamento esse endpoint finaliza a venda, gera uma mensagem de confirmação e um comprovante")
    @PostMapping
    fun finalizeSale(@RequestBody @Valid sale: SaleDto): ResponseEntity<CompletedSaleView> {
        val finalizeSale = saleService.finalizeSale(sale.toEntity())
        return ResponseEntity.status(HttpStatus.CREATED).body(CompletedSaleView(finalizeSale))
    }

    @Operation(summary = "informações das vendas", description = "endpoint que mostra todos os comprovantes de vendas realizados")
    @GetMapping
    fun showReceipts(): ResponseEntity<List<ProofOfSaleView>> {
        val listProofOfSale = saleService.showReceipts().stream()
            .map { proofOfSale: ProofOfSale -> ProofOfSaleView(proofOfSale) }.collect(Collectors.toList())
        return ResponseEntity.status(HttpStatus.OK).body(listProofOfSale)
    }

    @Operation(summary = "informação de um comprovante", description = "endpoint que mostra um comprovante específico")
    @GetMapping("/{id}")
    fun findVoucherById(@PathVariable id: Long): ResponseEntity<ProofOfSaleView> {
        val voucher = saleService.findVoucherById(id)
        return ResponseEntity.status(HttpStatus.OK).body(ProofOfSaleView(voucher))
    }

    @Operation(summary = "excluir comprovante de venda", description = "endpoint para excluir um comprovante de venda")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteReceiptById(@PathVariable id: Long) {
        saleService.deleteReceiptById(id)
    }
}