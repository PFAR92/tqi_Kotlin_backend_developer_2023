package com.tqi.kotlinbackenddeveloper2023.domain.service.sale.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.Sale
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.ProofOfSale
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.VoucherProduct
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import com.tqi.kotlinbackenddeveloper2023.domain.repository.sale.ProofOfSaleRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.sale.VoucherProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart.ShoppingCartRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.sale.ISaleService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.stream.Collectors

@Service
@Transactional
class SaleService(
    private val voucherProductRepository: VoucherProductRepository,
    private val proofOfSaleRepository: ProofOfSaleRepository,
     private val shoppingCartRepository: ShoppingCartRepository
) : ISaleService {
    override fun finalizeSale(sale: Sale): ProofOfSale {

        val shoppingCart = shoppingCartRepository.findById(1L).orElseThrow {
            throw BusinessException("first create a cart and then finalize the sale")
        }
        if (shoppingCart.listProductsPlacedInTheCart.isEmpty()) {throw BusinessException("add a product to the cart before finalizing the sale")}

        sale.totalSaleAmount = shoppingCart.cartValue

        val proofOfSale = ProofOfSale(0L, LocalDateTime.now(), shoppingCart.cartValue,sale.paymentOptions.toString(), mutableListOf())
        val savedProofOfSale = proofOfSaleRepository.save(proofOfSale)

        val listVoucherProduct = shoppingCart.listProductsPlacedInTheCart.map { voucherProduct ->
            VoucherProduct(
                productName = voucherProduct.product.name,
                productQuantity = voucherProduct.quantity,
                productUnitPrice = voucherProduct.product.unitPrice,
                productValue = voucherProduct.price,
                proofOfSale = savedProofOfSale
            )
        }
        voucherProductRepository.saveAll(listVoucherProduct)
        savedProofOfSale.voucherProduct.addAll(listVoucherProduct)
        shoppingCartRepository.deleteById(1L)
        proofOfSaleRepository.save(savedProofOfSale)



        return savedProofOfSale
    }

    override fun showReceipts(): List<ProofOfSale> {
        return proofOfSaleRepository.findAll()
    }

    override fun findVoucherById(id: Long): ProofOfSale {
        TODO("Not yet implemented")
    }

    override fun deleteReceiptById(id: Long) {
        TODO("Not yet implemented")
    }
}