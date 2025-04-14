package com.example.weatherforecast.ui.screen.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.api.NetworkResponse
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherResult = MutableLiveData<NetworkResponse<WeatherModel>>()
    val weatherResult : LiveData<NetworkResponse<WeatherModel>> = _weatherResult //stateflow

    fun getWeatherData(city: String) {
        runCatching {
            viewModelScope.launch {
                _weatherResult.value = NetworkResponse.Loading

                val result = repository.getWeather(city)
                if (result != null) {
                        _weatherResult.value = NetworkResponse.Success(result)
                    }
                else {
                    _weatherResult.value = NetworkResponse.Error("Failed to fetch weather data")
                }
            }
        }.onFailure {
            Log.e("WeatherViewModel", "Error load weather data", it)
            _weatherResult.value = NetworkResponse.Error("Error load weather data")
        }
    }
}
