package com.tqi.kotlinbackenddeveloper2023.domain.service.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.PaymentOptions
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.Sale
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ShoppingCart
import com.tqi.kotlinbackenddeveloper2023.domain.repository.sale.ProofOfSaleRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.sale.VoucherProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart.ShoppingCartRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.sale.impl.SaleService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.math.BigDecimal
import java.util.*

class SaleServiceTest {

    private lateinit var proofOfSaleRepository: ProofOfSaleRepository
    private lateinit var voucherProductRepository: VoucherProductRepository
    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var saleService: SaleService

    @BeforeEach
    fun setUp() {
        proofOfSaleRepository = mock(ProofOfSaleRepository::class.java)
        voucherProductRepository = mock(VoucherProductRepository::class.java)
        shoppingCartRepository = mock(ShoppingCartRepository::class.java)
        saleService = SaleService(voucherProductRepository, proofOfSaleRepository, shoppingCartRepository)
    }

    @Test
    fun `finalizing a sale without a cart throws an exception`() {

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.empty())

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            saleService.finalizeSale(buildSale())
        }
        val expectedMessage = "first create a cart and then finalize the sale"
        Assertions.assertEquals(expectedMessage, exception.message)
    }

    @Test
    fun `finalizing a sale with an empty cart throws an exception`() {

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(buildShoppingCart()))

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            saleService.finalizeSale(buildSale())
        }
        val expectedMessage = "add a product to the cart before finalizing the sale"
        Assertions.assertEquals(expectedMessage, exception.message)
    }

    private fun buildSale(
        totalSaleAmount: BigDecimal = BigDecimal.ZERO,
        paymentOptions: PaymentOptions = PaymentOptions.DINHEIRO
    ) = Sale(
        totalSaleAmount = totalSaleAmount,
        paymentOptions = paymentOptions
    )


    private fun buildShoppingCart(
        id: Long = 1L,
        cartValues: BigDecimal = BigDecimal.ZERO,
        listProductsPlacedInTheCart: MutableList<ProductsPlacedInTheCart> = mutableListOf(),
    ) = ShoppingCart(
        id = id,
        cartValue = cartValues,
        listProductsPlacedInTheCart = listProductsPlacedInTheCart
    )

}