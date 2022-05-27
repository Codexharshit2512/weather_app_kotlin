package com.example.sunshine.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sunshine.models.Forecast

class DetailsViewModel(data:Forecast.Daily):ViewModel(){
    val data=MutableLiveData<Forecast.Daily>()
    init{
        Log.i("from details vm","initialized")
        Log.i("from details vm","$data")
        this.data.value=data
    }
}