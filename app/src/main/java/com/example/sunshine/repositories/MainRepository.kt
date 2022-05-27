package com.example.sunshine.repositories

import com.example.sunshine.models.Forecast
import com.example.sunshine.network.ApiClient
import com.example.sunshine.network.WeatherApi
import retrofit2.Response

class MainRepository {
    suspend fun fetchForecast(lat: String, lon: String, units: String): Response<Forecast> {
        val client = ApiClient.getWeatherClient().create(WeatherApi::class.java)
        return client.getForecastData(
            lat,
            lon,
            "minutely,alerts",
            ApiClient.getKey(),
            units
        )
    }
}