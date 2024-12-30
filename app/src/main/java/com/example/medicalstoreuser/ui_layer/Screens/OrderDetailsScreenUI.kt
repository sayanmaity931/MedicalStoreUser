package com.example.medicalstoreuser.ui_layer.Screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalstoreuser.ui_layer.AppViewModel
import com.example.medicalstoreuser.ui_layer.navigation.Routes
import com.example.medicalstoreuser.user_pref.UserPreferenceManager

@Composable
fun OrderDetailsScreenUI(product_id : String , navController: NavController , userPreferenceManager : UserPreferenceManager) {

    val viewModel = hiltViewModel<AppViewModel>()

    val context = LocalContext.current

    val state = viewModel.createOrderResponse.collectAsState()

    val state2 = viewModel.getSpecificProductResponse.collectAsState()

    val user = userPreferenceManager.userID.collectAsState(initial = true)

    val quantity = remember { mutableStateOf("") }

    val orderDate = remember { mutableStateOf("") }

    when{
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }
        state.value.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.value.error.toString())
            }
        }
        state.value.data != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = state.value.data.toString())
                Toast.makeText(LocalContext.current, "Order Placed" , Toast.LENGTH_SHORT).show()
            }
        }
        else -> {

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
               OutlinedTextField(
                   value = quantity.value.toString(),
                   onValueChange = { quantity.value = it},
                   label = {
                       Text(text = "Enter Quantity")
                   }
               )
                OutlinedTextField(
                    value = orderDate.value,
                    onValueChange = {
                        orderDate.value = it
                    },
                    label = {
                        Text(text = "Enter Order Date")
                    }
                )
                Button(
                    onClick = {
                        if(quantity.value > state2.value.data?.body()?.stock.toString()){
                            Toast.makeText(context, "Out of Stock" , Toast.LENGTH_SHORT).show()
                        }
                        else {
                            viewModel.createOrder(
                                userId = user.value.toString(),
                                productId = product_id,
                                quantity = quantity.value,
                                orderDate = orderDate.value
                            )
                            navController.navigate(Routes.OrderHistory)
                        }
                    }
                ) {
                    Text(text = "Place Order")
                }
            }
        }
    }
}