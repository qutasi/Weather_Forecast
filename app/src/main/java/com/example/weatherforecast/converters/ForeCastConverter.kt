package com.example.weatherforecast.converters

import androidx.room.TypeConverter
import com.example.weatherforecast.model.Forecast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ForeCastConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromForecast(forecast: Forecast): String {
        return gson.toJson(forecast)
    }

    @TypeConverter
    fun toForecast(data: String): Forecast {
        val type = object : TypeToken<Forecast>() {}.type
        return gson.fromJson(data, type)
    }
}