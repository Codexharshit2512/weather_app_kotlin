package com.example.sunshine.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sunshine.models.Forecast
import com.example.sunshine.network.ApiClient
import com.example.sunshine.network.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModal:ViewModel() {
    var forecast = MutableLiveData<MutableList<Forecast>>()
    var currentForecast = MutableLiveData<Forecast.Current>()
    var dailyForecast = MutableLiveData<MutableList<Forecast.Daily>>()
    var hourlyForecast = MutableLiveData<MutableList<Forecast.Hourly>>()
    var loading=MutableLiveData<Boolean>()
    var currentIndex=MutableLiveData<Int>()
    init{
        Log.i("from mv","hello")
        forecast.value= mutableListOf()
        dailyForecast.value= mutableListOf()
        hourlyForecast.value= mutableListOf()
        loading.value=false
        currentIndex.value=-1
    }
    fun addForecast(data:Forecast){
        forecast.value?.add(data)
        incrementIndex()
        currentForecast.value=data.current
        dailyForecast.value=data.daily.toMutableList()
        hourlyForecast.value=data.hourly.toMutableList()
    }

    fun decrementIndex(){
        currentIndex.value?.minus(1)
    }


    fun incrementIndex(){
        currentIndex.value?.plus(1)
    }

    fun setIndex(index:Int){
        currentIndex.value=index
//        currentForecast.value=data.current
        dailyForecast.value=forecast.value!![index].daily.toMutableList()
//        hourlyForecast.value=data.hourly.toMutableList()
    }

}