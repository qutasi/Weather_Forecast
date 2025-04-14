package com.example.weatherforecast.repository

import android.util.Log
import com.example.weatherforecast.api.Constant
import com.example.weatherforecast.api.WeatherApi
import com.example.weatherforecast.data.dao.WeatherDao
import com.example.weatherforecast.model.WeatherModel
import javax.inject.Inject

class WeatherRepoImpl @Inject constructor(
    private val api: WeatherApi,
    private val dao: WeatherDao
) : WeatherRepository {

    override suspend fun getWeather(city: String): WeatherModel? {
        val response = api.getWeather(Constant.apiKey, city)
        return if (response.isSuccessful) {
            response.body()?.also {
                Log.d("WeatherRepoImpl", "Fetch city: ${it.location.name}")
            }
        } else {
            null
        }
    }
}