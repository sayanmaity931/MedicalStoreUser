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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.medicalstoreuser.ui_layer.navigation.Routes.HomeScreen
import com.example.medicalstoreuser.ui_layer.navigation.Routes.SignUpScreen
import com.example.medicalstoreuser.user_pref.UserPreferenceManager

@Composable
fun LogInScreenUI(navController: NavController,viewModel: AppViewModel = hiltViewModel() , userPreferenceManager: UserPreferenceManager) {

    val state = viewModel.loginResponse.collectAsState()

    val context = LocalContext.current

    val response = remember {  mutableStateOf<Int?>(null) }

    val userPassword = remember { mutableStateOf("") }

    val userEmail = remember { mutableStateOf("") }

    when{
        state.value.isLoading -> {
            CircularProgressIndicator()
        }
        state.value.error != null -> {
            Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
        }
        state.value.data != null -> {
            Log.d("LogInScreen", "Login Successful and user id is ${state.value.data?.message()}")
            LaunchedEffect(Unit) {
                userEmail.value = ""
                userPassword.value = ""
            }
        }
    }


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
                value = userEmail.value,
                onValueChange = {
                    userEmail.value = it
                },
                placeholder = { Text("Enter Your Email") }
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

            LaunchedEffect(response) {
                if(state.value.data != null) {
                    response.let {
                        val message = if (it.value == 200) {
                            "Login Successful"
                        } else {
                            "Failed to Login"
                        }
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(context, "Please fill all the data", Toast.LENGTH_SHORT).show()
                }
            }

            Button(
                onClick = {
                if(userEmail.value.isNotEmpty() && userPassword.value.isNotEmpty()) {
                    viewModel.logInView(
                        email = userEmail.value,
                        password = userPassword.value
                    )
                    response.value = 200
                    navController.navigate(HomeScreen)
                    }
                    else{
                        Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    }
                }
            ) {
                Text(text = "Login")
            }

            Spacer(modifier = Modifier.size(20.dp))

            MultiColorText("Don't have an account? ", "SignUp" , modifier = Modifier.clickable{
                navController.navigate(SignUpScreen) {
                        navController.navigateUp() // It clears the present screen
                    }
                }
            )
        }
    }
}