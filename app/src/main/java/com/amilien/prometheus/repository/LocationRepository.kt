package com.amilien.prometheus.repository

import android.content.Context
import android.location.Address
import android.location.Geocoder
import com.amilien.prometheus.model.WeatherData
import com.amilien.prometheus.model.api.ApiService
import com.amilien.prometheus.utils.*
import com.google.android.gms.maps.model.LatLng
import java.util.*

private const val UNKNOWN = "Unknown"

class LocationRepository(
    private val context: Context,
    private val apiService: ApiService
) {

    var location: LatLng = DEFAULT_LOCATION
    var weather: WeatherData? = null

    val placeName: String
        get(): String {
            val gcd = Geocoder(context, Locale.getDefault())
            val addresses: List<Address> = gcd.getFromLocation(location.latitude, location.longitude, 1)
            return if (addresses.isNotEmpty()) {
                addresses[0].locality ?: UNKNOWN
            } else {
                UNKNOWN
            }
        }

    suspend fun getWeather(): WeatherData {
        return apiService.getWeather(API_KEY, location.latitude, location.longitude, API_PARAMS)
            .checkNetworkAndAwaitResponse(context)
            .handleResponse()
            .toWeatherData(context, placeName)
            .also { weather = it }
    }
}
