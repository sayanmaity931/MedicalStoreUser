package com.example.medicalstoreuser.ui_layer.Screens

import android.R.attr.onClick
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.rounded.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.medicalstoreuser.R
import com.example.medicalstoreuser.data_layer.response.getAllProductsResponseItem
import com.example.medicalstoreuser.data_layer.response.getSpecificProductResponse
import com.example.medicalstoreuser.ui_layer.AppViewModel
import com.example.medicalstoreuser.ui_layer.navigation.Routes
import com.example.medicalstoreuser.ui_layer.navigation.Routes.ProductDetailsScreen
import com.example.medicalstoreuser.user_pref.UserPreferenceManager
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

@Composable
fun HomeScreenUI(navController : NavController , userPreferenceManager: UserPreferenceManager ) {

    val viewModel: AppViewModel = hiltViewModel()

    val state = viewModel.getAllProductsResponse.collectAsState()

    val recentlyViewedProducts = viewModel.recentlyViewedProducts.collectAsState()

    when {
        state.value.isLoading -> {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Loading")
            }
        }

        state.value.error != null -> {
            Log.e("Error", "Error: ${state.value.error}")
        }

        state.value.data != null -> {

            val user = userPreferenceManager.userID
            Log.d("TAG", "Data rendering is Successful")

            LaunchedEffect(Unit) {
                viewModel.getSpecificUser(user.toString())
            }

            LazyColumn{

                item {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextField(
                                value = "",
                                onValueChange = {
                                    navController.navigate(Routes.SearchScreen)
                                },
                                trailingIcon = {
                                    Icon(
                                        imageVector = Icons.Filled.Search,
                                        contentDescription = null
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                                    .clickable{
                                        navController.navigate(Routes.SearchScreen)
                                    }
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))

                        Banner(navController = navController)

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(text = "Hot Topics" , style = androidx.compose.material3.MaterialTheme.typography.titleLarge)

                        Spacer(modifier = Modifier.height(6.dp))

                        Column(
                            modifier = Modifier.wrapContentHeight()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .width(255.dp),
                                    elevation = CardDefaults.cardElevation(8.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.logo),
                                            contentDescription = null,
                                            modifier = Modifier.clip(CircleShape).size(75.dp)
                                        )
                                        Text(text = "Very Good")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Bad")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Naughty")
                                    }
                                }
                                Card(
                                    modifier = Modifier
                                        .padding(6.dp).width(255.dp),
                                    elevation = CardDefaults.cardElevation(8.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.logo),
                                            contentDescription = null,
                                            modifier = Modifier.clip(CircleShape).size(75.dp)
                                        )
                                        Text(text = "Very Good")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Bad")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Naughty")
                                    }
                                }
                                Card(
                                    modifier = Modifier
                                        .padding(6.dp).width(255.dp),

                                    elevation = CardDefaults.cardElevation(8.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.logo),
                                            contentDescription = null,
                                            modifier = Modifier.clip(CircleShape).size(75.dp)
                                        )
                                        Text(text = "Very Good")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Bad")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Naughty")
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(6.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Card(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .width(255.dp),
                                    elevation = CardDefaults.cardElevation(8.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.logo),
                                            contentDescription = null,
                                            modifier = Modifier.clip(CircleShape).size(75.dp)
                                        )
                                        Text(text = "Very Good")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Bad")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Naughty")
                                    }
                                }
                                Card(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .width(255.dp),
                                    elevation = CardDefaults.cardElevation(8.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.logo),
                                            contentDescription = null,
                                            modifier = Modifier.clip(CircleShape).size(75.dp)
                                        )
                                        Text(text = "Very Good")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Bad")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Naughty")
                                    }
                                }
                                Card(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .width(255.dp),
                                    elevation = CardDefaults.cardElevation(8.dp)
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.logo),
                                            contentDescription = null,
                                            modifier = Modifier.clip(CircleShape).size(75.dp)
                                        )
                                        Text(text = "Very Good")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Bad")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Very Naughty")
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "Today's Most Bought", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)

                    Spacer(modifier = Modifier.height(6.dp))

                    Button(
                        onClick = {viewModel.clearLogIn()
                        navController.navigate(Routes.StartScreen)
                        }
                    ) {
                        Text(text = "Order History")
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    val likedProducts = viewModel.likeAddedResponse.collectAsState()

                    LazyRow {

                        items(state.value.data?.body() ?: emptyList()) { product ->

                            val isLiked = likedProducts.value.contains(product.product_id)

                            val iconValue = remember {  mutableStateOf(if (isLiked) Icons.Rounded.ThumbUp else Icons.Outlined.ThumbUp )}

                            Card(
                                modifier = Modifier
                                    .padding(6.dp)
                                    .wrapContentSize(),
                                elevation = CardDefaults.cardElevation(8.dp),
                                onClick = {
                                    navController.navigate(ProductDetailsScreen(product.product_id))
                                    viewModel.addProduct(product.product_id)
                                }
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.logo),
                                        contentDescription = null,
                                        modifier = Modifier.clip(CircleShape).size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    IconButton(
                                        onClick = {
                                            if (isLiked) {
                                                viewModel.removeLike(product.product_id) // Remove like
                                                iconValue.value = Icons.Outlined.ThumbUp
                                            } else {
                                                viewModel.addLike(product.product_id) // Add like
                                                iconValue.value = Icons.Rounded.ThumbUp
                                            }
                                        },
                                    ) {
                                        Icon(imageVector = iconValue.value, contentDescription = null)

                                        Log.d("UI", "Icon for product ${product.product_id}: ${if (isLiked) "Liked" else "Not Liked"}")
                                    }
                                }
                                Text(text = "Product Name : " + product.product_name)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Product Price : " + product.price.toString())
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = "Expiry Date : " + product.expiry_date)
                            }
                            Log.d("UI", "LazyRow recomposed with likedProducts: ${likedProducts.value}")
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = "Recently Viewed" , style = androidx.compose.material3.MaterialTheme.typography.titleLarge)

                    Spacer(modifier = Modifier.height(6.dp))

                    LazyRow {
                        items(recentlyViewedProducts.value) { productId ->

                            val product =
                                state.value.data?.body()?.find { it.product_id == productId }

                            if (product != null) {

                                Card(
                                    modifier = Modifier
                                        .padding(6.dp)
                                        .wrapContentSize(),
                                    elevation = CardDefaults.cardElevation(8.dp)
                                ) {
                                    Column(modifier = Modifier.padding(8.dp)) {
                                        Text(text = "Product Name: ${product.product_name}")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Price: ${product.price}")
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(text = "Expiry Date: ${product.expiry_date}")
                                    }
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Banner(navController: NavController ) {

    val imageList = listOf(
        R.drawable.photo1,
        R.drawable.photo5,
        R.drawable.photo6,
        R.drawable.photo7
    )

    val pagerState = rememberPagerState(pageCount = { imageList.size })

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        while (true) {
            delay(2000)
            pagerState.animateScrollToPage((pagerState.currentPage + 1) % imageList.size)
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier.wrapContentSize()) {
            HorizontalPager(
                state = pagerState,

                modifier = Modifier.size(300.dp)
            ) { currentPage ->

                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(26.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    Image(
                        painter = painterResource(id = imageList[currentPage]),
                        contentDescription = null,
                        contentScale = ContentScale.FillWidth
                    )
                }
            }
            IconButton(
                onClick = {
                    val nextPage = pagerState.currentPage + 1
                    if (nextPage < imageList.size) {
                        scope.launch {
                            pagerState.animateScrollToPage(nextPage)
                        }
                    }
                },
                modifier = Modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0x52373737)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowForward, contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }
            IconButton(
                onClick = {
                    val previousPage = pagerState.currentPage - 1
                    if (previousPage >= 0) {
                        scope.launch {
                            pagerState.animateScrollToPage(previousPage)
                        }
                    }
                },
                modifier = Modifier
                    .padding(30.dp)
                    .size(48.dp)
                    .align(Alignment.CenterStart)
                    .clip(CircleShape),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color(0x52373737)
                )
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack, contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    tint = Color.LightGray
                )
            }
        }

        PageIndicator(
            pageCount = imageList.size,
            currentPage = pagerState.currentPage
        )
    }
}

@Composable
fun PageIndicator(pageCount: Int, currentPage: Int) {

    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ){
       repeat(pageCount){
           IndicatorDots(isSelected = it == currentPage)
       }
    }
}

@Composable
fun IndicatorDots(isSelected: Boolean) {

    val size = animateDpAsState(targetValue = if (isSelected) 12.dp else 10.dp , label = "")
    Box(modifier = Modifier
        .padding(2.dp)
        .size(size.value)
        .clip(CircleShape)
        .background(if (isSelected) Color(0xff373737) else Color(0xA8373737))
    )

}




