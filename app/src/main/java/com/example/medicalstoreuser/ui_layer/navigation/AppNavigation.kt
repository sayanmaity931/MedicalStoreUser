package com.example.medicalstoreuser.ui_layer.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.medicalstoreuser.ui_layer.AppViewModel
import com.example.medicalstoreuser.ui_layer.Screens.CartListUI
import com.example.medicalstoreuser.ui_layer.Screens.CategoriesUI
import com.example.medicalstoreuser.ui_layer.Screens.HomeScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.LogInScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.OrderDetailsScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.OrderHistoryUI
import com.example.medicalstoreuser.ui_layer.Screens.ProductDetailsScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.ProfileUI
import com.example.medicalstoreuser.ui_layer.Screens.SearchScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.SignUpScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.StartScreenUI
import com.example.medicalstoreuser.ui_layer.Screens.UnverifiedScreenUI
import com.example.medicalstoreuser.user_pref.UserPreferenceManager
import kotlinx.coroutines.DelicateCoroutinesApi


@SuppressLint("CoroutineCreationDuringComposition", "SuspiciousIndentation")
@OptIn(DelicateCoroutinesApi::class)
@Composable
fun AppNavigation ( userPreferenceManager: UserPreferenceManager)
{
    val navController = rememberNavController()

    val viewModel : AppViewModel = hiltViewModel()

    val state = viewModel.getSpecificUserResponse.collectAsState()

    when{
        state.value.error != null -> {
            Log.d("AppNavigation" , "${state.value.error}")
        }
        state.value.data != null -> {
            Log.d("AppNavigation" , "${state.value.data?.message()}")
        }
        else -> {
            Log.d("AppNavigation" , "Wait")
        }
    }

    val userInfo = state.value.data?.body()

    val userId = userPreferenceManager.userID.collectAsState(initial = null)

    var selected = remember { mutableIntStateOf(0) }

    val currentDestinationAsState = navController.currentBackStackEntryAsState()

    var currentDestination = currentDestinationAsState.value?.destination?.route

    val shouldShowBottomBar = remember { mutableStateOf(true) }

    LaunchedEffect(currentDestination) {
        shouldShowBottomBar.value = when(currentDestination){
            Routes.LogInScreen::class.qualifiedName, Routes.SignUpScreen::class.qualifiedName -> false
            else -> true
        }
    }

    LaunchedEffect(Unit) {
        Log.d("AppNavigation", "Fetching user with ID: ${userId.value}")
        viewModel.getSpecificUser(userId.value.toString())
    }

    val bottomNavItems = listOf(
        bottomNavItem(
            name = "Home",
            icon = Icons.Rounded.Home
        ),
        bottomNavItem(
            name = "Categories",
            icon = Icons.Rounded.List
        ),
        bottomNavItem(
            name = "Your Cart",
            icon = Icons.Rounded.ShoppingCart
        ),
        bottomNavItem(
            name = "Profile",
            icon = Icons.Rounded.Person
        )
    )

    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar.value) {
                NavigationBar {
                    bottomNavItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = selected.intValue == index,
                            onClick = {
                                selected.intValue = index

                                when (selected.intValue) {

                                    0 -> navController.navigate(Routes.HomeScreen)

                                    1 -> navController.navigate(Routes.Categories)

                                    2 -> navController.navigate(Routes.CartList)

                                    3 -> navController.navigate(Routes.Profile(
                                        userInfo?.name.toString() ?: "N/A",
                                        userInfo?.email.toString() ?: "N/A",
                                        userInfo?.phone_number.toString() ?: "N/A",
                                        userInfo?.address.toString() ?: "N/A",
                                        userInfo?.pinCode.toString() ?: "N/A"
                                        )
                                    )
                                }
                            },
                            icon = { Icon(imageVector = item.icon, contentDescription = null) },
                            label = { Text(text = item.name) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            NavHost(navController = navController, startDestination = Routes.StartScreen) {

                composable<Routes.StartScreen> {
                    StartScreenUI(navController, userPreferenceManager)
                }

                composable<Routes.LogInScreen> {
                    LogInScreenUI(navController, userPreferenceManager = userPreferenceManager)
                }

                composable<Routes.SignUpScreen> {
                    SignUpScreenUI(navController)
                }

                composable<Routes.HomeScreen> {
                    HomeScreenUI(navController , userPreferenceManager)
                }

                composable<Routes.CartList> {
                    CartListUI(navController = navController )
                }

                composable<Routes.OrderHistory> {
                    OrderHistoryUI(navController)
                }

                composable<Routes.Profile> {
                    val data = it.toRoute<Routes.Profile>()
                    ProfileUI(
                        name = data.name,
                        email = data.email,
                        phoneNumber = data.phoneNumber,
                        address = data.address,
                        pinCode = data.pinCode,
                        navController
                    )
                }

                composable<Routes.ProductDetailsScreen>{
                    val data = it.toRoute<Routes.ProductDetailsScreen>()
                    ProductDetailsScreenUI(product_id = data.product_id , navController = navController)
                }

                composable<Routes.Categories> {
                    CategoriesUI(navController)
                }

                composable<Routes.UnverifiedScreen>{
                    UnverifiedScreenUI()
                }

                composable<Routes.SearchScreen>{
                    SearchScreenUI(navController = navController)
                }

                composable<Routes.OrderDetailsScreen>{
                    val data = it.toRoute<Routes.OrderDetailsScreen>()
                    OrderDetailsScreenUI(
                        product_id = data.product_id, navController = navController, userPreferenceManager = userPreferenceManager)
                }
            }
        }
    }
}

data class bottomNavItem(
    val name : String,
    val icon : ImageVector
)


