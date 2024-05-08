package com.example.vkinternshiptask.buisness.productList.presentation

import android.app.Application
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyStaggeredGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.example.vkinternshiptask.MainActivity
import com.example.vkinternshiptask.R
import com.example.vkinternshiptask.buisness.UiState
import com.example.vkinternshiptask.buisness.Product
import com.example.vkinternshiptask.di.factory.ProductListViewModelFactory
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProductList(
    productListViewModelFactory: ProductListViewModelFactory,
    application: Application,
    onNavigate: (String) -> Unit
) {
    val productListViewModel: ProductListViewModel = viewModel(
        factory = productListViewModelFactory.create(application)
    )

    val uiState = productListViewModel.uiState
    val listState = rememberLazyStaggeredGridState()
    val query = rememberSaveable { mutableStateOf("") }
    val products = remember {
        mutableStateListOf<Product>()
    }

    Column {
        SearchField(query, productListViewModel)
        Spacer(modifier = Modifier.height(8.dp))
        ProductsList(uiState, products, query, listState, onNavigate)
        if (uiState.value.isLoading)
            CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
    }
    ObserveLazyListState(listState, query, uiState, productListViewModel)
    ErrorImage(uiState)
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SearchField(
    query: MutableState<String>,
    productListViewModel: ProductListViewModel
) {
    TextField(
        modifier = Modifier.padding(start = 8.dp),
        colors = TextFieldDefaults.textFieldColors(
            containerColor = Color.Transparent
        ),
        value = query.value,
        onValueChange = {
            query.value = it
            productListViewModel.getProducts(query.value)
        },
        label = { Text(text = stringResource(R.string.search)) },

        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = "Search icon"
            )
        },
    )
}

@Composable
private fun ErrorImage(uiState: State<UiState<MutableList<Product>>>) {
    if (uiState.value.error is UiState.Error.RequestError) {
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
private fun ProductsList(
    uiState: State<UiState<MutableList<Product>>>,
    products: SnapshotStateList<Product>,
    query: MutableState<String>,
    listState: LazyStaggeredGridState,
    onNavigate: (String) -> Unit
) {
    if (uiState.value.data != null) {
        if (query.value.isNotBlank())
            products.clear()
        products.addAll(uiState.value.data ?: emptyList())

        LazyVerticalStaggeredGrid(
            modifier = Modifier.padding(vertical = 3.dp, horizontal = 3.dp),
            columns = StaggeredGridCells.Adaptive(150.dp),
            verticalItemSpacing = 4.dp,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            state = listState
        ) {
            items(products) {
                ProductCard(it, onNavigate)
            }
        }
    }
}

@Composable
private fun ObserveLazyListState(
    listState: LazyStaggeredGridState,
    query: MutableState<String>,
    uiState: State<UiState<MutableList<Product>>>,
    productListViewModel: ProductListViewModel
) {
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collectLatest { index ->
                if (index != null && index >= uiState.value.data!!.size - 1 && query.value.isBlank()) {
                    productListViewModel.getProducts(skip = index)
                }
            }
    }
}

@Composable
private fun ProductCard(it: Product, onNavigate: (String) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(Color(220, 220, 220, 250)),
        modifier = Modifier
            .fillMaxSize()
            .clip(RectangleShape)
            .clickable {
                onNavigate(MainActivity.PRODUCT_ROUTE.replace("{id}", it.id.toString()))
            },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box()
        {
            ProductImage(it)

            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 9.dp)
                    .clip(RoundedCornerShape(100.dp))
                    .background(
                        Color(102, 102, 102, 200)
                    ),
            ) {
                val title = if (it.title.length > 10)
                    it.title.substring(0, 10)
                else
                    it.title
                Text(
                    modifier = Modifier.padding(horizontal = 9.dp),
                    text = title,
                    color = Color.White,
                    fontSize = 15.sp
                )
            }
        }
        Text(text = it.description)
    }
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ProductImage(it: Product) {
    GlideImage(
        modifier = Modifier.clip(RectangleShape),
        model = it.thumbnail,
        contentScale = ContentScale.Crop,
        contentDescription = "Product Image",
        loading = placeholder(R.drawable.ic_launcher_foreground),
        failure = placeholder(R.drawable.ic_launcher_foreground)
    )
}