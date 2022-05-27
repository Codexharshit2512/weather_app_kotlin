package com.example.sunshine.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val baseUrl = "https://api.openweathermap.org"
    val geocodeBaseUrl="https://api.mapbox.com/"
    private val key="099c6bd7ef83984469f3e1cecfb97b2e"
    private val iconUrl="http://openweathermap.org/img/wn/"
    private val geoCodeKey="pk.eyJ1Ijoia2Vua2FuZWtpMjUxMiIsImEiOiJjazZxNndtdmIwd21lM2ttOThjbHJ5Y3lsIn0.5lPV8ljtvu8Ua-6Awxjr_Q"
    fun getWeatherClient(): Retrofit {
        var retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit
    }

    fun getAutoCompleteClient():Retrofit{
        var gson=GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

        var retrofit = Retrofit.Builder()
            .baseUrl(geocodeBaseUrl)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
        return retrofit
    }

    fun getKey():String{
        return key
    }

    fun getIconUrl():String{
        return iconUrl
    }

    fun getGeoKey():String{
        return geoCodeKey
    }

}