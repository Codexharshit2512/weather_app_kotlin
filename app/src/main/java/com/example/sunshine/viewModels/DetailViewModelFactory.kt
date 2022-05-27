package com.example.sunshine.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sunshine.models.Forecast

class DetailViewModelFactory(private val data:Forecast.Daily):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(data) as T
//        if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
//            return DetailsViewModel(data) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
    }
}