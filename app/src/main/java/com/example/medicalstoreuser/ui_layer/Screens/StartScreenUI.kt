package com.example.medicalstoreuser.ui_layer.Screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.medicalstoreuser.ui_layer.navigation.Routes.HomeScreen
import com.example.medicalstoreuser.ui_layer.navigation.Routes.LogInScreen
import com.example.medicalstoreuser.ui_layer.navigation.Routes.StartScreen
import com.example.medicalstoreuser.user_pref.UserPreferenceManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun StartScreenUI(navController: NavHostController, userPreferenceManager: UserPreferenceManager) {

    var userId = userPreferenceManager.userID.collectAsState(initial = null)

    Log.d("StartScreenUI", "User ID: ${userId.value}")

    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        GlobalScope.launch(Dispatchers.Main) {
            val startDestination = if (userId.value != null) HomeScreen else LogInScreen
            navController.navigate(startDestination){
                popUpTo(StartScreen){
                    inclusive = true
                }
            }
        }
    }
}
