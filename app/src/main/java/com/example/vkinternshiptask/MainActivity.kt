package com.example.vkinternshiptask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.vkfuture.utils.network.ConnectivityObserver
import com.example.vkfuture.utils.network.NetworkConnectionObserver
import com.example.vkinternshiptask.buisness.productCard.presentation.ProductCard
import com.example.vkinternshiptask.buisness.productCard.presentation.ProductCardViewModel
import com.example.vkinternshiptask.di.App
import com.example.vkinternshiptask.di.component.ProductComponent
import com.example.vkinternshiptask.di.factory.ProductListViewModelFactory
import com.example.vkinternshiptask.buisness.productList.presentation.ProductList
import com.example.vkinternshiptask.buisness.productList.presentation.ProductListViewModel
import com.example.vkinternshiptask.di.factory.ProductViewModelFactory
import com.example.vkinternshiptask.ui.theme.VkInternshipTaskTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    lateinit var productComponent: ProductComponent

    @Inject
    lateinit var productListViewModelFactory: ProductListViewModelFactory
    @Inject
    lateinit var productViewModelFactory: ProductViewModelFactory

    companion object {
        const val PRODUCT_LIST_ROUTE = "product_list"
        const val PRODUCT_ROUTE = "product/{id}"
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        productComponent = (application as App).applicationComponent.productComponent().create()
        productComponent.inject(this)


        val networkObserver = NetworkConnectionObserver(this)
        val observer = networkObserver.observe()

        var networkIsConnected by mutableStateOf(networkObserver.isConnected)

        lifecycleScope.launch {
            observer.collect {
                if (it == ConnectivityObserver.Status.Available)
                    networkIsConnected = true
                if (it == ConnectivityObserver.Status.Lost)
                    networkIsConnected = false
            }
        }

        setContent {
            val navController = rememberNavController()
            val currentRoute = navController.currentBackStack.collectAsState()
            VkInternshipTaskTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(topBar = {
                        TopAppBar(
                            title = {
                                if (currentRoute.value.getOrNull(currentRoute.value.lastIndex)?.destination?.route == PRODUCT_ROUTE)
                                    IconButton(onClick = { navController.popBackStack() }) {
                                        Icon(
                                            imageVector = Icons.Default.ArrowBack,
                                            contentDescription = "Back"
                                        )
                                    }
                                else
                                    Row {
                                        Text(text = getString(R.string.products))
                                        Spacer(modifier = Modifier.width(3.dp))

                                        if (!networkIsConnected)
                                            Icon(
                                                painter = painterResource(R.drawable.network_connection),
                                                contentDescription = null
                                            )
                                    }
                            },
                        )
                    })
                    {
                        NavHost(
                            modifier = Modifier.padding(it),
                            navController = navController,
                            startDestination = PRODUCT_LIST_ROUTE
                        ) {
                            composable(PRODUCT_LIST_ROUTE) {
                                ProductList(productListViewModelFactory, application) {
                                    navController.navigate(it)
                                }
                            }
                            composable(
                                route = PRODUCT_ROUTE,
                                arguments = listOf(navArgument("id")  {})
                            ) {
                                ProductCard(productViewModelFactory, it.arguments?.getString("id")?: "0")
                            }
                        }
                    }
                }
            }
        }
    }
}