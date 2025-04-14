package com.example.weatherforecast.converters

import androidx.room.TypeConverter
import com.example.weatherforecast.model.Current
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrentDataConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromCurrent(current: Current): String {
        return gson.toJson(current)
    }

    @TypeConverter
    fun toCurrent(data : String): Current {
        val type = object : TypeToken<Current>() {}.type
        return gson.fromJson(data, type)
    }
}