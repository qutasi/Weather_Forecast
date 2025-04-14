package com.example.weatherforecast.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weatherforecast.converters.CurrentDataConverter
import com.example.weatherforecast.converters.DateConverter
import com.example.weatherforecast.converters.ForeCastConverter
import com.example.weatherforecast.converters.LocationConverter
import com.example.weatherforecast.data.dao.WeatherDao
import com.example.weatherforecast.model.WeatherModel

@Database(entities = [WeatherModel::class], version = 1, exportSchema = false)
@TypeConverters(
    CurrentDataConverter::class,
    DateConverter::class,
    ForeCastConverter::class,
    LocationConverter::class
    )
abstract class WeatherDB : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}

