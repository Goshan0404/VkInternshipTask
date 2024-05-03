package com.example.vkinternshiptask.productList.domain

import com.example.vkinternshiptask.Resources

interface ProductListRepository {

    suspend fun getProducts(skip: Int): Resources<List<com.example.vkinternshiptask.productList.domain.model.Product>>
}