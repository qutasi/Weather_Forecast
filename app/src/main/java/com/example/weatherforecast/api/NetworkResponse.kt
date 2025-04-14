package com.example.weatherforecast.api

import kotlin.Nothing

//T refers to any type of data (WeatherModel)
sealed class NetworkResponse<out T> {

    data class Success<out T>(val data : T) : NetworkResponse<T>()
    data class Error(val message : String) : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()
}