package com.tqi.kotlinbackenddeveloper2023.domain.service.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.category.Category
import com.tqi.kotlinbackenddeveloper2023.domain.model.product.Product
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.PaymentOptions
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.Sale
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.ProofOfSale
import com.tqi.kotlinbackenddeveloper2023.domain.model.sale.proofOfSale.VoucherProduct
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ShoppingCart
import com.tqi.kotlinbackenddeveloper2023.domain.repository.sale.ProofOfSaleRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.sale.VoucherProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart.ShoppingCartRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.sale.impl.SaleService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.math.BigDecimal
import java.time.LocalDateTime
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

    @Test
    fun `at the end of the purchase, the list of products goes into the voucher, the voucher is saved and the shopping cart is deleted`() {

        val sale = buildSale()
        val shoppingCart = buildShoppingCart(1L, BigDecimal.ZERO, mutableListOf(buildProductInCart()))

        `when`(shoppingCartRepository.findById(1L)).thenReturn(Optional.of(shoppingCart))
        `when`(proofOfSaleRepository.save(any())).thenReturn(buildProofOfSale())

        val finalizingTheSale = saleService.finalizeSale(sale)

        Assertions.assertEquals(finalizingTheSale.value, shoppingCart.cartValue)

        verify(voucherProductRepository, times(1)).saveAll(anyList())
        verify(shoppingCartRepository, times(1)).deleteById(1L)
        verify(proofOfSaleRepository, times(2)).save(any())

    }

    @Test
    fun `fetching voucher with a non-existent id should return an exception`() {

        `when`(proofOfSaleRepository.findById(buildProofOfSale().id)).thenReturn(Optional.empty())

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            saleService.findVoucherById(buildProofOfSale().id)
        }
        val expectedMessage = "id ${buildProofOfSale().id} not found"
        Assertions.assertEquals(expectedMessage, exception.message)
    }

    @Test
    fun `findVoucherById returns the matching voucher`() {

        `when`(proofOfSaleRepository.findById(buildProofOfSale().id)).thenReturn(Optional.of(buildProofOfSale()))

        val proofOfSale = proofOfSaleRepository.findById(buildProofOfSale().id).get()

        Assertions.assertEquals(proofOfSale, buildProofOfSale())
        verify(proofOfSaleRepository, times(1)).findById(buildProofOfSale().id)
    }

    @Test
    fun `deleteReceiptById throws exception when voucher id is non-existent`() {

        `when`(proofOfSaleRepository.findById(buildProofOfSale().id)).thenReturn(Optional.empty())

        val exception = Assertions.assertThrows(BusinessException::class.java) {
            saleService.deleteReceiptById(buildProofOfSale().id)
        }
        val expectedMessage = "id ${buildProofOfSale().id} not found"
        Assertions.assertEquals(expectedMessage, exception.message)

        verify(proofOfSaleRepository, times(1)).findById(buildProofOfSale().id)
        verify(proofOfSaleRepository, times(0)).deleteById(buildProofOfSale().id)
    }

    @Test
    fun `deleteReceiptById deletes the requested receipt`() {

        `when`(proofOfSaleRepository.findById(buildProofOfSale().id)).thenReturn(Optional.of(buildProofOfSale()))

        saleService.deleteReceiptById(buildProofOfSale().id)

        verify(proofOfSaleRepository, times(1)).findById(buildProofOfSale().id)
        verify(proofOfSaleRepository, times(1)).delete(buildProofOfSale())
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

    private fun buildProductInCart(
        id: Long = 1L,
        shoppingCart: ShoppingCart = ShoppingCart(1L, BigDecimal.ZERO, mutableListOf<ProductsPlacedInTheCart>()),
        product: Product = Product(
            1L, "Coca Cola", Product.UnitOfMeasure.UNIDADE, BigDecimal.valueOf(7.50), Category(1L, "Bebidas")
        ),
        quantity: Int = 2,
        price: BigDecimal = BigDecimal.valueOf(15.00)
    ) = ProductsPlacedInTheCart(
        id = id,
        shoppingCart = shoppingCart,
        product = product,
        quantity = quantity,
        price = price
    )

    private fun buildProofOfSale(
        id: Long = 0L,
        dateTime: LocalDateTime = LocalDateTime.of(2023, 7, 28, 15, 20),
        value: BigDecimal = BigDecimal.ZERO,
        paymentOptions: PaymentOptions = PaymentOptions.DINHEIRO,
        voucherProduct: MutableList<VoucherProduct> = mutableListOf()
    ) = ProofOfSale(
        id = id,
        dateTime = dateTime,
        value = value,
        paymentOptions = paymentOptions.toString(),
        voucherProduct = voucherProduct
    )

}