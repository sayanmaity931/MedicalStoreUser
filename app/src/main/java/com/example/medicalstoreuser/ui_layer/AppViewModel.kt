package com.example.medicalstoreuser.ui_layer

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.medicalstoreuser.State1
import com.example.medicalstoreuser.data_layer.response.LogInResponse
import com.example.medicalstoreuser.data_layer.response.createOrderResponse
import com.example.medicalstoreuser.data_layer.response.getAllOrdersResponse
import com.example.medicalstoreuser.data_layer.response.getAllProductsResponse
import com.example.medicalstoreuser.data_layer.response.getAllProductsResponseItem
import com.example.medicalstoreuser.data_layer.response.getSpecificOrderResponse
import com.example.medicalstoreuser.data_layer.response.getSpecificProductResponse
import com.example.medicalstoreuser.data_layer.response.getSpecificUserResponse
import com.example.medicalstoreuser.data_layer.response.signUpResponse
import com.example.medicalstoreuser.repo.Repo
import com.example.medicalstoreuser.user_pref.UserPreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AppViewModel @Inject constructor(private val repo: Repo , private val userPreferenceManager: UserPreferenceManager) : ViewModel() {

    val signUpUserState = mutableStateOf<Response<signUpResponse>?>(null)

    private val _logInResponse = MutableStateFlow(LoginState())
    val loginResponse = _logInResponse.asStateFlow()

    private val _getAllProductsResponse = MutableStateFlow(GetAllProductsState())
    val getAllProductsResponse = _getAllProductsResponse.asStateFlow()

    private val _getSpecificProductResponse = MutableStateFlow(GetSpecificProductState())
    val getSpecificProductResponse = _getSpecificProductResponse.asStateFlow()

    private val _recentlyViewedProducts = MutableStateFlow<List<String>>(emptyList())
    val recentlyViewedProducts: StateFlow<List<String>> = _recentlyViewedProducts.asStateFlow()

    private val _cartAddedResponse = MutableStateFlow<List<String>>(emptyList())
    val cartAddedResponse: StateFlow<List<String>> = _cartAddedResponse.asStateFlow()

    private val _likeAddedResponse = MutableStateFlow<List<String>>(emptyList())
    val likeAddedResponse: StateFlow<List<String>> = _likeAddedResponse.asStateFlow()

    private val _createOrderResponse = MutableStateFlow(CreateOrderState())
    val createOrderResponse = _createOrderResponse.asStateFlow()

    private val _getAllOrdersResponse = MutableStateFlow(GetAllOrdersState())
    val getAllOrdersResponse = _getAllOrdersResponse.asStateFlow()

    private val _getSpecificOrderResponse = MutableStateFlow(GetSpecificOrderState())
    val getSpecificOrderResponse = _getSpecificOrderResponse.asStateFlow()

    private val _getSpecificUserResponse = MutableStateFlow(GetSpecificUserState())
    val getSpecificUserResponse = _getSpecificUserResponse.asStateFlow()

    fun createOrder(userId : String, productId : String, quantity : String, orderDate : String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.createOrderRepo(userId, productId, quantity, orderDate).collect {
                when (it) {
                    is State1.Loading -> {
                        _createOrderResponse.value = CreateOrderState(isLoading = true)
                    }
                    is State1.Success -> {
                        _createOrderResponse.value = CreateOrderState(data = it.data, isLoading = false)
                    }
                    is State1.Error -> {
                        _createOrderResponse.value = CreateOrderState(error = it.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun getAllOrders(){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getAllOrdersRepo().collect{
                when(it){
                    is State1.Loading -> {
                        _getAllOrdersResponse.value = GetAllOrdersState(isLoading = true)
                    }
                    is State1.Success -> {
                        _getAllOrdersResponse.value = GetAllOrdersState(data = it.data, isLoading = false)
                    }
                    is State1.Error -> {
                        _getAllOrdersResponse.value = GetAllOrdersState(error = it.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun getSpecificOrder(order_id : String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificOrderRepo(order_id).collect {
                when (it) {
                    is State1.Loading -> {
                        _getSpecificOrderResponse.value = GetSpecificOrderState(isLoading = true)
                    }
                    is State1.Success -> {
                        _getSpecificOrderResponse.value = GetSpecificOrderState(data = it.data, isLoading = false)
                    }
                    is State1.Error -> {
                        _getSpecificOrderResponse.value = GetSpecificOrderState(error = it.message, isLoading = false)
                    }
                }
            }
        }

    }

    fun getSpecificUser(user_id : String){
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificUserRepo(user_id).collect {
                when (it) {
                    is State1.Loading -> {
                        _getSpecificUserResponse.value = GetSpecificUserState(isLoading = true)
                    }
                    is State1.Success -> {
                        _getSpecificUserResponse.value = GetSpecificUserState(data = it.data, isLoading = false)
                    }
                    is State1.Error -> {
                        _getSpecificUserResponse.value = GetSpecificUserState(error = it.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun addCart(product_id: String) {
        viewModelScope.launch {
            userPreferenceManager.saveProductIdForCart(product_id)
            Log.d("AppViewModel", "Product added to cart: $product_id")
        }
    }

    fun addLike(product_id: String) {
        viewModelScope.launch {
            val updatedList = _likeAddedResponse.value.toMutableList().apply {
                if (!contains(product_id)) add(product_id)
            }
            userPreferenceManager.saveProductIdForLike(product_id) // Save to UserPreference
            Log.d("ViewModel2", "Updated liked products: $updatedList")
        }
    }

    fun removeLike(productId: String) {
        viewModelScope.launch {
            userPreferenceManager.removeLikedProduct(productId) // Save to UserPreference
            Log.d("ViewModel", "Removed liked product: $productId")
        }
    }

    fun loadLikedProducts() {
        viewModelScope.launch {
            userPreferenceManager.getProductID2s().collect { likedProducts ->
                _likeAddedResponse.value = likedProducts // Update the state
                Log.d("ViewModel1", "Updated liked products: $likedProducts")
            }
        }
    }

    fun signUpView(
        name: String,
        email: String,
        phoneNumber: String,
        address: String,
        password: String,
        pinCode: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            signUpUserState.value = repo.signUpUser(
                name = name,
                email = email,
                phoneNumber = phoneNumber,
                password = password,
                pinCode = pinCode,
                address = address
            )
        }
    }

    fun logInView(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.logInUser(email = email, password = password)
                .collect {
                    when (it) {
                        is State1.Loading -> {
                            _logInResponse.value = LoginState(isLoading = true)
                        }

                        is State1.Success -> {
                            val userId = it.data?.message()
                            if (userId != null) {
                                userPreferenceManager.saveUserID(userId)
                                Log.d("TAG quit", "userId saved of $userId")
                            } else {
                                Log.d("TAG", "userId not found ")
                            }
                            _logInResponse.value = LoginState(data = it.data, isLoading = false)
                        }

                        is State1.Error -> {
                            _logInResponse.value = LoginState(error = it.message, isLoading = false)
                        }
                    }
                }
        }
    }

    fun getAllProducts() {
        viewModelScope.launch() {
            repo.getAllProducts().collect {
                when (it) {
                    is State1.Loading -> {
                        _getAllProductsResponse.value = GetAllProductsState(isLoading = true)
                    }

                    is State1.Success -> {
                        _getAllProductsResponse.value =
                            GetAllProductsState(data = it.data, isLoading = false)
                    }

                    is State1.Error -> {
                        _getAllProductsResponse.value =
                            GetAllProductsState(error = it.message, isLoading = false)
                    }
                }
            }
        }
    }

    fun clearLogIn(){
        viewModelScope.launch{
            userPreferenceManager.clearUserId()
            Log.d("ViewModel#","LogIn id should be clear")
        }
    }

    fun getSpecificProduct(product_id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.getSpecificProductRepo(product_id).collect {
                when (it) {
                    is State1.Loading -> {
                        _getSpecificProductResponse.value =
                            GetSpecificProductState(isLoading = true)
                    }

                    is State1.Success -> {
                        val productId = it.data?.message()
                        if (productId != null) {
                            userPreferenceManager.saveProductID(productId)
                            Log.d("TAG", "productId saved ")
                        } else {
                            Log.d("TAG", "productId not found ")
                        }
                        _getSpecificProductResponse.value =
                            GetSpecificProductState(data = it.data, isLoading = false)

                    }

                    is State1.Error -> {
                        _getSpecificProductResponse.value =
                            GetSpecificProductState(error = it.message, isLoading = false)
                    }
                }
            }
        }
    }

    init {
        viewModelScope.launch {
            userPreferenceManager.getProductIDs().collect { productIds ->
                _recentlyViewedProducts.value = productIds
            }
        }
        viewModelScope.launch {
            userPreferenceManager.getProductID1s().collect { product ->
                _cartAddedResponse.value = product
                Log.d("AppViewModel", "Cart IDs Updated: $product")
            }
        }
        loadLikedProducts()
        getAllOrders()
    }

    fun addProduct(productId: String) {
        viewModelScope.launch {
            userPreferenceManager.saveProductID(productId)
        }
    }

    init {
        getAllProducts()
        Log.d("TEST","${userPreferenceManager.userID}")
    }

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _products = MutableStateFlow<List<getAllProductsResponseItem>>(emptyList())

    @OptIn(FlowPreview::class)
    val products = _searchText
        .debounce(500L) // To limit frequent updates
        .onEach { _isSearching.update { true } }
        .combine(_getAllProductsResponse) { text, state ->
            val allProducts = state.data?.body() ?: emptyList() // Extract product list
            if (text.isBlank()) {
                allProducts
            } else {
                allProducts.filter { it.matchesQuery(text) }
            }
        }
        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            _products.value
        )

    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

}

fun getAllProductsResponseItem.matchesQuery(query: String): Boolean {
    val matchCombinations = listOf(
        this.product_name // Example: Add more fields if necessary
    )
    return matchCombinations.any {
        it.contains(query, ignoreCase = true)
    }
}

data class LoginState(
    val isLoading : Boolean = false,
    val data : Response<LogInResponse>? = null,
    val error : String? = null
)

data class GetAllProductsState(
    val isLoading : Boolean = false,
    val data : Response<getAllProductsResponse>? = null,
    val error : String? = null
)

data class GetSpecificProductState(
    val isLoading : Boolean = false,
    val data : Response<getSpecificProductResponse>? = null,
    val error : String? = null
)

data class CreateOrderState(
    val isLoading : Boolean = false,
    val data : Response<createOrderResponse>? = null,
    val error : String? = null
)

data class GetAllOrdersState(

    val isLoading : Boolean = false,
    val data : Response<getAllOrdersResponse>? = null,
    val error : String? = null
)

data class GetSpecificOrderState(

    val isLoading : Boolean = false,
    val data : Response<getSpecificOrderResponse>? = null,
    val error : String? = null

)

data class GetSpecificUserState(

    val isLoading : Boolean = false,
    val data : Response<getSpecificUserResponse>? = null,
    val error : String? = null

)



