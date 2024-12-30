package com.example.medicalstoreuser

// Here T is used for generic type
// sealed class is that class where u can use that class in that class also
sealed class State<T> {
    data class Success<T>(val data: T) : State<T>()
    data class Error<T>(val message: String) : State<T>()
    class Loading : State<Nothing>()

}