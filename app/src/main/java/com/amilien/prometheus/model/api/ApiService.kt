package com.amilien.prometheus.model.api

import com.amilien.prometheus.model.api.response.ApiWeatherData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/v3/weather/realtime")
    fun getWeather(
        @Query("apikey") apiKey: String,
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("fields") fields: String
    ): Call<ApiWeatherData>
}
