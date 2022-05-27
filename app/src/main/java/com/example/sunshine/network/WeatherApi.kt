package com.example.sunshine.network

import com.example.sunshine.models.Forecast
import com.example.sunshine.models.SearchResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WeatherApi {
    @GET("/data/2.5/onecall")
    suspend fun getForecastData(@Query("lat") lat:String,@Query("lon") lon:String,
    @Query("exclude") exclude:String,@Query("appid") appid:String
    ,@Query("units") units:String
                                      ): Response<Forecast>
    @GET("geocoding/v5/mapbox.places/{query}.json")
    suspend fun getSearchResults(@Path("query") query:String
    ,@Query("access_token") token:String
    ) : Response<SearchResult>
}