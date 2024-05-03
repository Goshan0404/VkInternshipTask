package com.example.vkinternshiptask.productList.view

import android.content.Context
import android.net.ConnectivityManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.vkfuture.utils.network.ConnectivityObserver
import com.example.vkinternshiptask.R
import com.example.vkinternshiptask.UiState


@Composable
fun ProductList(productListViewModel: ProductListViewModel, context: Context) {
    val uiState = productListViewModel.uiState

    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager



    val observer = productListViewModel.observer.collectAsState(initial = productListViewModel.networkObserver.isConnected)

    if (uiState.value.data != null)
        LazyVerticalGrid(
            modifier = Modifier.padding(vertical = 3.dp, horizontal = 3.dp),
            columns = GridCells.Adaptive(minSize = 150.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(uiState.value.data ?: emptyList()) {
                ProductCard(it)
            }
        }

    if (observer.value == ConnectivityObserver.Status.Lost) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {


            Image(
                painter = painterResource(id = R.drawable.network_connection_error),
                contentDescription = "Error"
            )
        }
    }

    if (uiState.value.error is UiState.Error.NetworkError
        && observer.value != ConnectivityObserver.Status.Lost) {
        Box(
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {


            Image(
                painter = painterResource(id = R.drawable.network_error),
                contentDescription = "Error"
            )
        }
    }
}

@Composable
private fun ProductCard(it: com.example.vkinternshiptask.productList.domain.model.Product) {
    Card(
        colors = CardDefaults.cardColors(Color(249, 249, 249, 250)),
        modifier = Modifier
            .fillMaxSize()
            .clip(RectangleShape)
    ) {
        Box(
            Modifier
                .fillMaxSize()
        )
        {

            ProductImage(it)

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .clip(CircleShape)
                    .padding(bottom = 6.dp)
                    .background(
                        Color(102, 102, 102, 80)
                    ),
            ) {
                Text(text = it.title, color = Color.White)
            }
        }

        Text(text = it.description)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ProductImage(it: com.example.vkinternshiptask.productList.domain.model.Product) {
    GlideImage(
        modifier = Modifier.clip(RectangleShape),
        model = it.thumbnail,
        contentScale = ContentScale.Crop,
        contentDescription = "Product Image",
        loading = placeholder(R.drawable.ic_launcher_foreground),
        failure = placeholder(R.drawable.ic_launcher_foreground)
    )
}