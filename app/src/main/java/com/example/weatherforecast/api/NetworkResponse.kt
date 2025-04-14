package com.example.weatherforecast.api

import kotlin.Nothing

//T refers to any type of data (WeatherModel)
sealed class NetworkResponse<out T> {

    object Empty : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()
    data class Error(val message : String) : NetworkResponse<Nothing>()
    data class Success<out T>(val data : T) : NetworkResponse<T>()

}