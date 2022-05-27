package com.example.sunshine.models

import com.google.gson.annotations.Expose
import java.io.Serializable

data class SearchResult(
    @Expose
    val features: List<Feature>,
    @Expose
    val query: List<String>,
    @Expose
    val type: String
){

    data class Feature(
        @Expose
        val center: List<Double>,
        @Expose
        val place_name: String,
        @Expose
        val text: String,

    ):Serializable
}