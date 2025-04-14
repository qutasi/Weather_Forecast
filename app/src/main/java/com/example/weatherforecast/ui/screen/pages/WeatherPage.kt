package com.example.weatherforecast.ui.screen.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.weatherforecast.api.NetworkResponse
import com.example.weatherforecast.model.WeatherModel
import com.example.weatherforecast.ui.screen.viewModel.WeatherViewModel

@Composable
fun WeatherPage(
    viewModel: WeatherViewModel,
    modifier: Modifier = Modifier
) {

    var city by remember { mutableStateOf("") } //use stateflow
    val weatherResult = viewModel.weatherResult.observeAsState() //collectstatewithlifecycle

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(modifier = modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = city,
                onValueChange = {city = it},
                textStyle = TextStyle(color = Color.Black),
                label = { Text(text = "Search for any location") },
            )
            IconButton(onClick = {
                viewModel.getWeatherData(city)
                keyboardController?.hide()
            }) {
                Icon(imageVector = Icons.Default.Search,
                    contentDescription = "Search for any location",
                    modifier = Modifier.size(40.dp))
            }
        }
        when(val result = weatherResult.value) {
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }
            is NetworkResponse.Loading -> {
                CircularProgressIndicator()
            }
            is NetworkResponse.Success -> {
                WeatherDetails(data = result.data)
            }
            else -> {}
        }
    }
}

@Composable
fun WeatherDetails(data : WeatherModel) {

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location icon",
                modifier = Modifier.size(40.dp)
            )
            Text(text = data.location.name, fontSize = 30.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country, fontSize = 18.sp, color = Color.Gray)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "${data.current.temp_c} °C ",
            fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))
        ConditionImage(data = data)

        Text(
            text = data.current.condition.text,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(16.dp))
        WeatherD(modifier = Modifier, data = data)
    }
}


@Composable
fun ConditionImage(data : WeatherModel) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data("https:${data.current.condition.icon}".replace("64x64", "128x128"))
            .crossfade(true)
            .build(),
        contentDescription = "Condition icon",
        modifier = Modifier.size(150.dp)
    )
}

@Composable
fun WeatherD(modifier: Modifier = Modifier, data : WeatherModel) {
    Card {
        Column(
            modifier = modifier.fillMaxWidth(),
        ) {
            Row(modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherKeyVal("Humidity", data.current.humidity.toString())
                WeatherKeyVal("Wind speed", "${data.current.wind_kph} km/h")

            }
            Row(modifier = modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                WeatherKeyVal("Feels like", "${data.current.feelslike_c} °C")
                WeatherKeyVal("Humidity", data.current.uv.toString())
            }
        }
    }
}

@Composable
fun WeatherKeyVal(key : String, value : String) {
    Column(modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = value, fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Text(text = key, color = Color.Gray, fontWeight = FontWeight.SemiBold)
    }
}






