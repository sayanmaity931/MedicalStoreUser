package com.example.medicalstoreuser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.medicalstoreuser.ui.theme.MedicalStoreUserTheme
import com.example.medicalstoreuser.ui_layer.navigation.AppNavigation
import com.example.medicalstoreuser.user_pref.UserPreferenceManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val userPreferenceManager = UserPreferenceManager(this)
        setContent {
            MedicalStoreUserTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->


                        Box(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxSize()
                        ) {
                            AppNavigation(userPreferenceManager)
                        }
                    }
                }
                }
            }
        }


