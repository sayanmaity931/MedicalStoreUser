package com.example.medicalstoreuser.ui_layer.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalstoreuser.ui_layer.AppViewModel
import com.example.medicalstoreuser.ui_layer.navigation.Routes

@Composable
fun SearchScreenUI(modifier: Modifier = Modifier , navController : NavController) {
    val viewModel: AppViewModel = hiltViewModel()

    val searchText = viewModel.searchText.collectAsState()
    val products = viewModel.products.collectAsState()
    val isSearching = viewModel.isSearching.collectAsState()

    Column(modifier = modifier.padding(16.dp)) {

        TextField(
            value = searchText.value,
            onValueChange = viewModel::onSearchTextChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Search products...") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isSearching.value) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            if(searchText.value.isBlank()){
                Text(text = "PLZ Enter any Product Name")
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(products.value) { product ->
                        Text(
                            text = product.product_name, // Assuming `name` is a field in `getAllProductsResponseItem`
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 16.dp)
                                .clickable(enabled = true , onClick = {
                                    navController.navigate(Routes.ProductDetailsScreen(product.product_id))
                                }
                                )
                        )
                    }
                }
            }
        }
    }
}
