package com.example.weatherforecast.converters

import androidx.room.TypeConverter
import com.example.weatherforecast.model.Forecast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ForeCastConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromForecast(forecast: Forecast?): String? {
        return forecast?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toForecast(data: String?): Forecast? {
        return data?.let {
            val type = object : TypeToken<Forecast>() {}.type
            gson.fromJson(it, type)
        }
    }
}