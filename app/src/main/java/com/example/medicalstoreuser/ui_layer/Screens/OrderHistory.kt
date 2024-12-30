package com.example.medicalstoreuser.ui_layer.Screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalstoreuser.ui_layer.AppViewModel

@Composable
fun OrderHistoryUI(navController: NavController) {

    val viewModel = hiltViewModel<AppViewModel>()
    val state =  viewModel.getAllOrdersResponse.collectAsState().value

    when{
        state.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), Alignment.Center){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        state.error != null -> {
            Box(modifier = Modifier.fillMaxSize(), Alignment.Center){
                Text(text = state.error)
            }
        }
        state.data != null -> {

            LazyColumn {
                items(state.data.body() ?: emptyList()){

                    LaunchedEffect(Unit) {
                        viewModel.getSpecificProduct(it.product_id)

                    }

                    Card(modifier = Modifier.fillMaxWidth().padding(16.dp)) {

                        Text(text = "ID : ${it.id}")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Order ID : ${it.order_id}")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Quantity : ${it.quantity}")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Total Price : ${it.total_price}")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Order Date : ${it.order_date}")
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(text = "Product Expiry Date : ${it.product_expiry_date}")
                        Spacer(modifier = Modifier.height(6.dp))
                        if (it.isApproved == 1){
                            Text(text = "Order Status : Order on the way")
                        }else{
                            Text(text = "Order Status : Pending")
                        }
                    }
                }
            }
        }
        else -> {
            Box(modifier = Modifier.fillMaxSize(), Alignment.Center){
                Text(text = "No Order History")
            }
        }
    }
}