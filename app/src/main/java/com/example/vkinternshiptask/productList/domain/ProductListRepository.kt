package com.example.vkinternshiptask.productList.domain

import com.example.vkinternshiptask.Resources
import com.example.vkinternshiptask.productList.domain.model.Product

interface ProductListRepository {

    suspend fun getProducts(skip: Int): Resources<List<Product>>
    suspend fun searchProducts(query: String): Resources<List<Product>>
}