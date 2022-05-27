package com.example.sunshine.models

import java.io.Serializable

data class Forecast(
    val current: Current,
    val daily: List<Daily>,
    val hourly: List<Hourly>,
    val lat: Double,
    val lon: Double,
    val timezone: String,
    val timezone_offset: Long,
    var place_name:String?
):Serializable{

    data class Current(
        val clouds: Double,
        val dew_point: Double,
        val dt: Long,
        val feels_like: Double,
        val humidity: Double,
        val pressure: Double,
        val sunrise: Long,
        val sunset: Long,
        val temp: Double,
        val uvi: Double,
        val visibility: Double,
        val weather: List<Weather>,
        val wind_deg: Double,
        val wind_speed: Double
    )

    data class Hourly(
        val clouds: Double,
        val dew_point: Double,
        val dt: Long,
        val feels_like: Double,
        val humidity: Double,
        val pop: Double,
        val pressure: Double,
        val temp: Double,
        val uvi: Double,
        val visibility: Double,
        val weather: List<Weather>,
        val wind_deg: Double,
        val wind_gust: Double,
        val wind_speed: Double
    )

    data class FeelsLike(
        val day: Double,
        val eve: Double,
        val morn: Double,
        val night: Double
    )

    data class Temp(
        val day: Double,
        val eve: Double,
        val max: Double,
        val min: Double,
        val morn: Double,
        val night: Double
    )

    data class Daily(
        val clouds: Double,
        val dew_point: Double,
        val dt: Long,
        val feels_like: FeelsLike,
        val humidity: Double,
        val moon_phase: Double,
        val moonrise: Long,
        val moonset: Long,
        val pop: Double,
        val pressure: Double,
        val rain: Double,
        val sunrise: Long,
        val sunset: Long,
        val temp: Temp,
        val uvi: Double,
        val weather: List<Weather>,
        val wind_deg: Double,
        val wind_gust: Double,
        val wind_speed: Double
    ):Serializable

    data class Weather(
        val description: String,
        val icon: String,
        val id: Int,
        val main: String
    )
}