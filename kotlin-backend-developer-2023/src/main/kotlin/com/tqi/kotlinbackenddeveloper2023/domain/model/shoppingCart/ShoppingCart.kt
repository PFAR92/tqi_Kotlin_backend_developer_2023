package com.tqi.kotlinbackenddeveloper2023.domain.model.shoppingCart

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class ShoppingCart(

    @Id
    var id: Long = 1L,

    @OneToMany(mappedBy = "shoppingCart")
    var listProductsPlacedInTheCart: MutableList<ProductsPlacedInTheCart>,

    )
