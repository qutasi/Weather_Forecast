package com.example.weatherforecast.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.weatherforecast.model.WeatherModel
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {
    @Query("SELECT * FROM weatherTable WHERE location LIKE :city LIMIT 1")
    suspend fun getWeather(city: String): WeatherModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeather(weather: WeatherModel)

    @Query("SELECT * FROM weatherTable ORDER BY timestamp DESC LIMIT 3")
    fun getLatestWeather(): Flow<WeatherModel?>
}
