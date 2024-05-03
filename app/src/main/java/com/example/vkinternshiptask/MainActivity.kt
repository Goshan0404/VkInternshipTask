package com.example.vkinternshiptask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.vkinternshiptask.di.App
import com.example.vkinternshiptask.di.component.ProductComponent
import com.example.vkinternshiptask.di.factory.ProductListViewModelFactory
import com.example.vkinternshiptask.productList.view.ProductList
import com.example.vkinternshiptask.productList.view.ProductListViewModel
import com.example.vkinternshiptask.ui.theme.VkInternshipTaskTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    lateinit var productComponent: ProductComponent
    val productListViewModel: ProductListViewModel by viewModels {
        factory.create(application)
    }

    @Inject
    lateinit var factory: ProductListViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productComponent = (application as App).applicationComponent.productComponent().create()
        productComponent.inject(this)

        setContent {
            VkInternshipTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductList(productListViewModel, this)
                }
            }
        }
    }
}