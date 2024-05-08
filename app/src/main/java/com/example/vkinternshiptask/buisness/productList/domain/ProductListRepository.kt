package com.example.vkinternshiptask.buisness.productList.domain

import com.example.vkinternshiptask.buisness.Product
import com.example.vkinternshiptask.buisness.Resources

interface ProductListRepository {

    suspend fun getProducts(skip: Int): Resources<List<Product>>
    suspend fun searchProducts(query: String): Resources<List<Product>>
}