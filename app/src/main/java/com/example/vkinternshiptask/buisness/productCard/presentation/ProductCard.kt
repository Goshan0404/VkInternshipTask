package com.example.vkinternshiptask.buisness.productCard.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.vkinternshiptask.R
import com.example.vkinternshiptask.buisness.Product
import com.example.vkinternshiptask.di.factory.ProductViewModelFactory

@OptIn(
    ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class
)
@Composable
fun ProductCard(productViewModelFactory: ProductViewModelFactory, id: String) {

    val productCardViewModel: ProductCardViewModel = viewModel(factory = productViewModelFactory.create(id))

    val uiState = productCardViewModel.uiState
    val pagerState = rememberPagerState(pageCount = {
        uiState.value.data?.images?.size ?: 0
    })


    if (uiState.value.isLoading)
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator(Modifier.align(Alignment.Center))
        }

    uiState.value.data?.let { product ->
        Column {
            ProductImages(pagerState, product)
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ProductTitle(product)
                Column {
                    Row {
                        ProductRating(product)
                    }
                    Row {
                        ProductPrice(product)
                    }
                }
            }
            Spacer(Modifier.height(10.dp))
            Text(
                modifier = Modifier.padding(start = 10.dp),
                text = product.description
            )
        }
    }


}

@Composable
private fun ProductPrice(product: Product) {
    Text(text = product.price.toString())
    Icon(imageVector = Icons.Default.Star, contentDescription = "star")
}

@Composable
private fun ProductRating(product: Product) {
    Text(text = product.rating.toString())
    Icon(
        painter = painterResource(id = R.drawable.money_24),
        contentDescription = null
    )
}

@Composable
private fun ProductTitle(product: Product) {
    Text(
        modifier = Modifier.padding(start = 10.dp),
        text = product.title,
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
@OptIn(ExperimentalFoundationApi::class, ExperimentalGlideComposeApi::class)
private fun ProductImages(
    pagerState: PagerState,
    product: Product
) {
    HorizontalPager(state = pagerState) {
        GlideImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            model = product.images[it],
            contentDescription = "Images"
        )
    }
}