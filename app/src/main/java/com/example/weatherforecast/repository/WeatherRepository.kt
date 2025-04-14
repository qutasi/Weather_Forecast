package com.example.weatherforecast.repository

import com.example.weatherforecast.model.WeatherModel
import kotlinx.coroutines.flow.Flow

interface WeatherRepository {

    suspend fun getWeather(city : String) : WeatherModel?

    suspend fun insertWeather(weather: WeatherModel)

    fun getLatestWeather() : Flow<WeatherModel?>

}