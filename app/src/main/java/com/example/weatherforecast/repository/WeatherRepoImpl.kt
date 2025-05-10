package com.example.weatherforecast.repository

import android.util.Log
import com.example.weatherforecast.api.Constant
import com.example.weatherforecast.api.WeatherApi
import com.example.weatherforecast.data.dao.WeatherDao
import com.example.weatherforecast.model.WeatherModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
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

    override fun getLatestWeather(): Flow<List<WeatherModel?>> {
        return dao.getLatestWeather()
    }

    override suspend fun insertWeather(weatherModel : WeatherModel) {
        dao.insertWeather(weatherModel)
    }

}