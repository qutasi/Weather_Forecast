package com.example.weatherforecast.di

import android.content.Context
import androidx.room.Room
import com.example.weatherforecast.api.WeatherApi
import com.example.weatherforecast.data.dao.WeatherDao
import com.example.weatherforecast.data.db.WeatherDB
import com.example.weatherforecast.repository.WeatherRepoImpl
import com.example.weatherforecast.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "https://api.weatherapi.com"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): WeatherDB {
        return Room.databaseBuilder(
            context,
            WeatherDB::class.java,
            "weather_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideWeatherApi() : WeatherApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherDao(db: WeatherDB): WeatherDao {
        return db.weatherDao()
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api: WeatherApi, dao: WeatherDao): WeatherRepository {
        return WeatherRepoImpl(api, dao)
    }
}
