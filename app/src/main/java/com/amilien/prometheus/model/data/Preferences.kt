package com.amilien.prometheus.model.data

import android.content.SharedPreferences
import com.amilien.prometheus.model.WeatherData
import com.amilien.prometheus.utils.fromJsonString
import com.amilien.prometheus.utils.putJsonString

class Preferences(private val sharedPreferences: SharedPreferences) {

    fun getWeatherList(): List<WeatherData> {
        return sharedPreferences.fromJsonString<MutableList<WeatherData>>(PREFERENCE_WEATHER_LIST) ?: mutableListOf()
    }

    fun saveWeather(weather: WeatherData) {
        val weatherList = getWeatherList() as MutableList<WeatherData>
        weatherList.add(weather)
        sharedPreferences.putJsonString(weatherList, PREFERENCE_WEATHER_LIST)
    }

    companion object {

        private const val PREFERENCE_WEATHER_LIST = "weather-list"
    }
}
