package com.example.vkinternshiptask.buisness.productCard.domain

import com.example.vkinternshiptask.buisness.Product
import com.example.vkinternshiptask.buisness.Resources


interface ProductRepository {
    suspend fun getProduct(id: String): Resources<Product>
}