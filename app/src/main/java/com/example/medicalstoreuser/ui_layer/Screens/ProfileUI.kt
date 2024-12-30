package com.example.medicalstoreuser.ui_layer.Screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.medicalstoreuser.ui_layer.common.MultiColorText
import com.example.medicalstoreuser.ui_layer.navigation.Routes
import com.example.medicalstoreuser.ui_layer.navigation.Routes.LogInScreen

@Composable
fun ProfileUI(name : String, email : String, phoneNumber : String, address : String, pinCode : String, navController: NavController) {

    LazyColumn (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        item {
            Card(
                modifier = Modifier.fillMaxWidth()
            ){
            IconButton(
                onClick = {

                }
            ) {
                Icon(imageVector = Icons.Filled.Person, contentDescription = null)
                }
            Icon(imageVector = Icons.Rounded.AddCircle , contentDescription = null )
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Name : $name")
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Email : $email")
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Phone Number : $phoneNumber")
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Address : $address")
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "Pin Code : $pinCode")
            Spacer(modifier = Modifier.height(6.dp))
            MultiColorText("Go To? ", "HomeScreen" , modifier = Modifier.clickable {
                navController.navigate(Routes.HomeScreen) {
                    navController.navigateUp()
                    }
                }
            )
        }
    }
}