package com.amilien.prometheus.model.api.response

import android.content.Context
import com.amilien.prometheus.R
import com.amilien.prometheus.model.WeatherData
import com.google.gson.annotations.SerializedName

class ApiWeatherData(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("weather_code") val weatherCode: ApiWeather,
    @SerializedName("temp") val temperature: ApiValueUnits,
    @SerializedName("wind_speed") val windSpeed: ApiValueUnits,
    @SerializedName("humidity") val humidity: ApiValueUnits
) {

    fun toWeatherData(context: Context, place: String): WeatherData {
        return WeatherData(
            place,
            lat,
            lon,
            weatherCode.value.getWeatherString(context),
            context.getString(R.string.temperature, temperature.value, temperature.units),
            context.getString(R.string.wind_speed_value, windSpeed.value, windSpeed.units),
            context.getString(R.string.humidity_value, humidity.value, humidity.units)
        )
    }
}

class ApiWeather(
    @SerializedName("value") val value: ApiWeatherCode
)

class ApiValueUnits(
    @SerializedName("value") val value: Double,
    @SerializedName("units") val units: String
)
