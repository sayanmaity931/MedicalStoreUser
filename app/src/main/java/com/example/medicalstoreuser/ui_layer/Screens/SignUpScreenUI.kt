package com.example.medicalstoreuser.ui_layer.Screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalstoreuser.R
import com.example.medicalstoreuser.ui_layer.AppViewModel
import com.example.medicalstoreuser.ui_layer.common.MultiColorText
import com.example.medicalstoreuser.ui_layer.navigation.Routes
import com.example.medicalstoreuser.ui_layer.navigation.Routes.LogInScreen
import com.example.medicalstoreuser.ui_layer.navigation.Routes.UnverifiedScreen

@Composable
fun SignUpScreenUI(navController: NavController , viewModel : AppViewModel = hiltViewModel() ) {

    val state = viewModel.signUpUserState.value

    val context = LocalContext.current

    if (state != null){
        if (state.isSuccessful){
            Log.d("SignUpScreen", state.body()?.user_id.toString())

        }else{
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            navController.navigate(UnverifiedScreen) {
                navController.navigateUp()
            }
        }
    }
    else{
        Toast.makeText(context, "Loading", Toast.LENGTH_SHORT).show()
    }

    var userName = remember { mutableStateOf("") }
    val userPassword = remember { mutableStateOf("") }
    val userEmail = remember { mutableStateOf("") }
    val userPhoneNumber = remember { mutableStateOf("") }
    val userPinCode = remember { mutableStateOf("") }
    val userAddress = remember { mutableStateOf("") }

    LazyColumn (modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally){
        item{
            Spacer(modifier = Modifier.size(20.dp))
            Image(painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
                modifier = Modifier.size(150.dp).clip(CircleShape)
            )
            Spacer(modifier = Modifier.size(20.dp))

            OutlinedTextField(
                value = userName.value,
                onValueChange = {
                    userName.value = it
                },
                placeholder = { Text("Enter Your Name") }
            )
            Spacer(modifier = Modifier.size(20.dp))

            OutlinedTextField(
                value = userEmail.value,
                onValueChange = {
                    userEmail.value = it
                },
                placeholder = { Text("Enter Your Email") }
            )

            Spacer(modifier = Modifier.size(20.dp))

            OutlinedTextField(
                value = userPhoneNumber.value,
                onValueChange = {
                    userPhoneNumber.value = it
                },
                placeholder = { Text("Enter Your Phone Number") }
            )
            Spacer(modifier = Modifier.size(20.dp))

            OutlinedTextField(
                value = userPassword.value,
                onValueChange = {
                    userPassword.value = it
                },
                placeholder = { Text("Enter Your Password") }
            )

            Spacer(modifier = Modifier.size(20.dp))

            OutlinedTextField(
                value = userPinCode.value,
                onValueChange = {
                    userPinCode.value = it
                },
                placeholder = { Text("Enter Your pinCode") }
            )
            Spacer(modifier = Modifier.size(20.dp))

            OutlinedTextField(
                value = userAddress.value,
                onValueChange = {
                    userAddress.value = it
                },
                placeholder = { Text("Enter Your Address") }
            )
            Spacer(modifier = Modifier.size(20.dp))

            Button(
                onClick = {
                    viewModel.signUpView(
                        userName.value,
                        userEmail.value,
                        userPhoneNumber.value,
                        userAddress.value,
                        userPassword.value,
                        userPinCode.value
                    )
                    navController.navigate(Routes.Profile(userName.value, userEmail.value, userPhoneNumber.value, userAddress.value, userPinCode.value))
                }
            ){
                Text(text = "Add User")
            }

            Spacer(modifier = Modifier.size(40.dp))

            MultiColorText("Already have an account? ", "Login" , modifier = Modifier.clickable{
                navController.navigate(LogInScreen){
                navController.navigateUp()}
                }
            )
        }
    }
}