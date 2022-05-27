package com.example.sunshine.viewModels

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.sunshine.R
import com.example.sunshine.models.Forecast
import com.example.sunshine.models.SearchResult
import com.example.sunshine.network.ApiClient
import com.example.sunshine.network.WeatherApi
import com.example.sunshine.repositories.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.create

class SearchViewModel:ViewModel() {
    var searchQuery = MutableLiveData<String>()
    var results = MutableLiveData<List<SearchResult.Feature>>()
    private var job:Job?
    private var repo:MainRepository?=null
    private var error=MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()
    init{
        searchQuery.value=""
        results.value= listOf()
        job=null
        repo= MainRepository()
        loading.value=false
        error.value=false
    }

    fun fetchSearchResults(){
        job?.cancel()
       val client=ApiClient.getAutoCompleteClient().create(WeatherApi::class.java)
       job= viewModelScope.launch {

               val response=client.getSearchResults(searchQuery.value!!,ApiClient.getGeoKey())
               if(response.isSuccessful){
                   Log.i("search results",response.body().toString())
                   withContext(Dispatchers.Main){
                       results.value=response.body()?.features
                   }

               }
               else{
                   Log.i("search error","search failed")
               }


       }
    }

    fun fetchLocationForecast(lat:String,lon:String,controller:NavController,item:SearchResult.Feature){
        loading.value=true
        viewModelScope.launch(Dispatchers.IO) {
            val response=repo?.fetchForecast(lat,lon,"metric")
            if(response!=null && response.isSuccessful){
                withContext(Dispatchers.Main){
                    error.value=false
                    loading.value=false
                    val bundle = Bundle()
                    val forecast=response.body()
                    forecast?.place_name=item.place_name
                    bundle.putSerializable("forecast",forecast)
                    controller.navigate(R.id.action_searchFragment_to_mainFragment,bundle)
                }
            }
            else{
                error.value=true
            }
        }
    }

}