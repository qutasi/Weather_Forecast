package com.example.weatherforecast.converters

import androidx.room.TypeConverter
import com.example.weatherforecast.model.Location
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocationConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromLocation(location: Location): String {
        return gson.toJson(location)
    }

    @TypeConverter
    fun toLocation(data: String): Location {
        //val type = object : TypeToken<Location>() {}.type
        //return gson.fromJson(data, type)

        return gson.fromJson(data, Location::class.java)
    }
}