package com.example.weatherforecast.ui.screen.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherforecast.api.NetworkResponse
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {

    private val _weatherResult = MutableStateFlow<NetworkResponse<WeatherModel>>(NetworkResponse.Empty)
    val weatherResult : StateFlow<NetworkResponse<WeatherModel>> = _weatherResult

    private val _latestWeather = MutableStateFlow<List<String?>>(emptyList())
    val latestWeather : StateFlow<List<String?>> = _latestWeather

    private val _lastCitiesWithWeather = MutableStateFlow<MutableList<NetworkResponse<WeatherModel>>>(mutableListOf())
    val lastCitiesWithWeather : StateFlow<List<NetworkResponse<WeatherModel>>> = _lastCitiesWithWeather

    init {
        getLatestCities()
        getLatestCityWeathers()
    }

    private fun getLatestCities() {
        viewModelScope.launch {
            repository.getLatestWeather()
                .collect { latest ->
                    _latestWeather.value = latest.map { it?.location?.name ?: "" }
                }
        }
    }

    private fun getLatestCityWeathers() {
        latestWeather.value.forEach {
            getWeatherData(it ?: "")
            _lastCitiesWithWeather.value.add(_weatherResult.value)
        }
    }

    // Called when user searches for a city
    fun getWeatherData(city: String) {
        runCatching {
            viewModelScope.launch {
                _weatherResult.value = NetworkResponse.Loading
                val result = repository.getWeather(city)

                if (result != null) {
                    _weatherResult.value = NetworkResponse.Success(result)
                    repository.insertWeather(result)

                } else {
                    _weatherResult.value = NetworkResponse.Error("Failed to fetch weather data")
                }
            }
        }.onFailure {
            Log.e("WeatherViewModel", "Error load weather data", it)
            _weatherResult.value = NetworkResponse.Error("Error load weather data")
        }
    }
}
