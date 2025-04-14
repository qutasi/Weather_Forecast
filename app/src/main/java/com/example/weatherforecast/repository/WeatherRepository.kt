package com.example.weatherforecast.repository

import com.example.weatherforecast.model.WeatherModel

interface WeatherRepository {

    suspend fun getWeather(city : String) : WeatherModel?

}