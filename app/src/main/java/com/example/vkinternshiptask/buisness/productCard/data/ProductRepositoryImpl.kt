package com.example.vkinternshiptask.buisness.productCard.data

import android.util.Log
import com.example.vkinternshiptask.buisness.productCard.domain.ProductRepository
import com.example.vkinternshiptask.buisness.Product
import com.example.vkinternshiptask.buisness.Resources
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val api: ProductApi) : ProductRepository {
    override suspend fun getProduct(id: String): Resources<Product> {
        return try {
            val response = api.getProduct(id).toProduct()
            Resources.Success(data = response)
        } catch (e: Exception) {
            Log.d("TAG", "getProduct: ${e.message}")
            Resources.Error(message = "Network Error")
        }
    }
}