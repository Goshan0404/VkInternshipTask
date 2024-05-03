package com.example.vkinternshiptask.productList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.vkinternshiptask.R
import com.example.vkinternshiptask.productList.remote.model.ProductRemote


val arr = listOf(
    ProductRemote(
        "New",
        "New",
        "New",
        2.2,
        124,
        listOf(),
        5,
        4.4,
        435,
        "https://cdn.dummyjson.com/product-images/2/thumbnail.jpg",
        "iPhone 9"
    ),
    ProductRemote(
        "New",
        "New",
        "New",
        2.2,
        124,
        listOf(),
        5,
        4.4,
        435,
        "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
        "hljl"
    ),
    ProductRemote(
        "New",
        "New",
        "New",
        2.2,
        124,
        listOf(),
        5,
        4.4,
        435,
        "https://cdn.dummyjson.com/product-images/2/thumbnail.jpg",
        "hljl"
    ),
    ProductRemote(
        "New",
        "New",
        "New",
        2.2,
        124,
        listOf(),
        5,
        4.4,
        435,
        "https://cdn.dummyjson.com/product-images/1/thumbnail.jpg",
        "hljl"
    ),
)

@Preview
@Composable
fun ProductList() {

    LazyVerticalGrid(
        modifier = Modifier.padding(vertical = 3.dp, horizontal = 3.dp),
        columns = GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(arr) {
            ProductCard(it)
        }
    }
}

@Composable
private fun ProductCard(it: ProductRemote) {
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
private fun ProductImage(it: ProductRemote) {
    GlideImage(
        modifier = Modifier.clip(RectangleShape),
        model = it.thumbnail,
        contentScale = ContentScale.Crop,
        contentDescription = "Product Image",
        loading = placeholder(R.drawable.ic_launcher_foreground),
        failure = placeholder(R.drawable.ic_launcher_foreground)
    )
}