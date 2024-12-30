package com.example.medicalstoreuser.ui_layer.Screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalstoreuser.ui_layer.AppViewModel

@Composable
fun CategoriesUI(navController: NavController) {

    val viewModel : AppViewModel = hiltViewModel()

    val state = viewModel.getAllProductsResponse.collectAsState()

    LazyColumn {

        items(state.value.data?.body() ?: emptyList()){

            Text(text = "Category : ${it.category}" , style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(6.dp))
        }
    }
}