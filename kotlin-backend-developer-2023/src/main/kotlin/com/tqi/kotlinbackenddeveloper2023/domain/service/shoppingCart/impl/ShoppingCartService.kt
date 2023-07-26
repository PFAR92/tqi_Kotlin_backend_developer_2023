package com.tqi.kotlinbackenddeveloper2023.domain.service.shoppingCart.impl

import com.tqi.kotlinbackenddeveloper2023.domain.exceptions.BusinessException
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ProductsPlacedInTheCart
import com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart.ShoppingCart
import com.tqi.kotlinbackenddeveloper2023.domain.repository.product.ProductRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart.ProductsPlacedInTheCartRepository
import com.tqi.kotlinbackenddeveloper2023.domain.repository.shoppingCart.ShoppingCartRepository
import com.tqi.kotlinbackenddeveloper2023.domain.service.shoppingCart.IShoppingCart
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
@Transactional
class ShoppingCartService(
    private val shoppingCartRepository: ShoppingCartRepository,
    private val productsPlacedInTheCartRepository: ProductsPlacedInTheCartRepository,
    private val productRepository: ProductRepository
): IShoppingCart {

    override fun addProductToCart(product: ProductsPlacedInTheCart): ShoppingCart {

        val shoppingCart = shoppingCartRepository.findById(1L).orElseGet {
            shoppingCartRepository.save(ShoppingCart(1L, mutableListOf()))
        }

        val productToBeInserted = productRepository.findByName(product.product.name).orElseThrow {
            throw BusinessException("The product entered does not exist, please check that it was entered correctly " +
                    "or add the product to stock")
        }

        val existingProduct = shoppingCart.listProductsPlacedInTheCart.find { it.product.id == productToBeInserted.id }
        if (existingProduct != null) {
            shoppingCart.listProductsPlacedInTheCart.remove(existingProduct)
            existingProduct.quantity += product.quantity
            existingProduct.price = existingProduct.product.unitPrice.multiply(existingProduct.quantity.toBigDecimal())
            productsPlacedInTheCartRepository.save(existingProduct)
            shoppingCart.listProductsPlacedInTheCart.add(existingProduct)
        } else {
            product.product = productToBeInserted
            product.price = product.product.unitPrice.multiply(product.quantity.toBigDecimal())
            productsPlacedInTheCartRepository.save(product)
            shoppingCart.listProductsPlacedInTheCart.add(product)
        }

        return shoppingCartRepository.save(shoppingCart)
    }

    override fun changeTheQuantityOfTheProduct(product: ProductsPlacedInTheCart): ShoppingCart {
        val shoppingCart = shoppingCartRepository.findById(1L).orElseThrow {
            throw BusinessException("Cart is empty, there are no products to update")
        }
        val existingProduct = shoppingCart.listProductsPlacedInTheCart.find { it.product.name == product.product.name }
            ?: throw BusinessException("There is no such product in the cart, please add it first before changing the quantity")

        if (product.quantity == 0) {
            shoppingCart.listProductsPlacedInTheCart.remove(existingProduct)
            productsPlacedInTheCartRepository.deleteById(existingProduct.id)
        } else {
            shoppingCart.listProductsPlacedInTheCart.remove(existingProduct)
            existingProduct.quantity = product.quantity
            existingProduct.price = existingProduct.product.unitPrice.multiply(product.quantity.toBigDecimal())
            productsPlacedInTheCartRepository.save(existingProduct)
            shoppingCart.listProductsPlacedInTheCart.add(existingProduct)
        }

        return shoppingCartRepository.save(shoppingCart)
    }

    override fun findShoppingCart(): ShoppingCart {
        return shoppingCartRepository.findById(1L).orElseThrow {
            throw BusinessException("Cart is empty, add products to view shopping cart")
        }
    }
}