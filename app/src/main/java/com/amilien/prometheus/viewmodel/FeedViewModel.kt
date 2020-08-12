package com.amilien.prometheus.viewmodel

import androidx.lifecycle.ViewModel
import com.amilien.prometheus.model.WeatherData
import com.amilien.prometheus.model.data.Preferences
import javax.inject.Inject

class FeedViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    val items: List<WeatherData>
        get() = preferences.getWeatherList()

    fun saveWeather(weather: WeatherData) {
        preferences.saveWeather(weather)
    }
}
