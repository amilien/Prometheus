package com.amilien.prometheus.model

class WeatherData(
    val place: String,
    val latitude: Double,
    val longitude: Double,
    val weather: String,
    val temperature: String,
    val windSpeed: String,
    val humidity: String
)
