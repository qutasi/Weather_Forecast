package com.example.weatherforecast.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weatherTable")
data class WeatherModel(
    @PrimaryKey val id: Int? = null,
    val location: Location,
    val current: Current,
    val forecast: Forecast? = null,
    val timestamp: Long = System.currentTimeMillis()
)