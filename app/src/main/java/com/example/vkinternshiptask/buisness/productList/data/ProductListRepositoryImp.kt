package com.example.vkinternshiptask.buisness.productList.data

import android.util.Log
import com.example.vkinternshiptask.buisness.Product
import com.example.vkinternshiptask.buisness.Resources
import com.example.vkinternshiptask.buisness.productList.domain.ProductListRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import java.lang.Exception
import javax.inject.Inject

class ProductListRepositoryImp @Inject constructor(private val productsApi: ProductsApi) :
    ProductListRepository {

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.d("Network", "Caught $exception")
    }

    override suspend fun getProducts(skip: Int): Resources<List<Product>> {

        return try {
            val response = productsApi.getProducts(skip = skip)

            if (response.isSuccessful) {
                Resources.Success(
                    response.body()?.products?.map { it.toProduct() }
                        ?: listOf())
            } else
                Resources.Error(message = response.message())
        } catch (e: Exception) {
            Resources.Error(message = "Network Exception")
        }
    }

    override suspend fun searchProducts(query: String): Resources<List<Product>> {
        return try {
            val response = productsApi.searchProducts(query)

            if (response.isSuccessful) {
                Resources.Success(
                    response.body()?.products?.map { it.toProduct() }
                        ?: listOf())
            } else
                Resources.Error(message = response.message())
        } catch (e: Exception) {
            Resources.Error(message = "Network Exception")
        }
    }
}