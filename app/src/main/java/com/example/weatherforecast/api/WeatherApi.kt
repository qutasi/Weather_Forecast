package com.example.weatherforecast.api

import com.example.weatherforecast.model.WeatherModel
import dagger.Provides
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("/v1/current.json")
        suspend fun getWeather(
            @Query("key") key: String,
            @Query("q") city: String
        ) : Response<WeatherModel>
}

//TODO: more fetching methods