package com.example.medicalstoreuser.ui_layer.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalstoreuser.ui_layer.AppViewModel
import com.example.medicalstoreuser.ui_layer.navigation.Routes

@Composable
fun ProductDetailsScreenUI(product_id : String, navController: NavController) {

    val viewModel : AppViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getSpecificProduct(product_id)
    }

    val state = viewModel.getSpecificProductResponse.collectAsState()

    val state1 = viewModel.cartAddedResponse.collectAsState()

    when{
        state1.value.isNotEmpty() -> {
           Toast.makeText(LocalContext.current, "Product added to cart", Toast.LENGTH_SHORT).show()
        }
        else -> {
            Toast.makeText(LocalContext.current, "Loading", Toast.LENGTH_SHORT).show()
        }
    }

    when{
        state.value.isLoading -> {
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){
                CircularProgressIndicator()
            }
        }
        state.value.error != null -> {
            Log.e("TAG","Error : ${state.value.error}")
        }
        state.value.data != null -> {
            Column (
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){

                        val product = state.value.data!!.body()
                        Text(text = "Product Name : "+ product?.product_name)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Product Price : "+ product?.price.toString())
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Expiry Date : "+product?.expiry_date)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Category : ${product?.category}")

                Button(
                    onClick = {
                        viewModel.getSpecificProduct(product_id)
                        navController.navigate(Routes.OrderDetailsScreen(product_id))
                    }
                ) {
                    Text(text = "Buy" , style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
                }

                Row (
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.End
                ){
                    IconButton(onClick = {
                        viewModel.addCart(product_id)
                    }
                    ){
                        Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                    }
                }
            }
        }
    }
}


