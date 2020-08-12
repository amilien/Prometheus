package com.amilien.prometheus.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amilien.prometheus.model.WeatherData
import com.amilien.prometheus.model.lifecycle.SingleLiveDataEvent
import com.amilien.prometheus.repository.LocationRepository
import com.amilien.prometheus.utils.launch
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MapsViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val locationRepository: LocationRepository
): ViewModel() {

    val weatherLiveData = SingleLiveDataEvent<WeatherData>()

    var location: LatLng
        get() = locationRepository.location
        set(value) {
            locationRepository.location = value
        }

    val weather: WeatherData?
        get() = locationRepository.weather

    val placeName: String
        get() = locationRepository.placeName

    fun getWeather() {
        viewModelScope.launch(weatherLiveData) {
            withContext(dispatcher) { locationRepository.getWeather() }
        }
    }
}
