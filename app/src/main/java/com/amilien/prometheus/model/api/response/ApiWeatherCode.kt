package com.amilien.prometheus.model.api.response

import android.content.Context
import com.amilien.prometheus.R
import com.google.gson.annotations.SerializedName

enum class ApiWeatherCode {
    @SerializedName("freezing_rain_heavy") FREEZING_RAIN_HEAVY,
    @SerializedName("freezing_rain") FREEZING_RAIN,
    @SerializedName("freezing_rain_light") FREEZING_RAIN_LIGHT,
    @SerializedName("freezing_drizzle") FREEZING_DRIZZLE,
    @SerializedName("ice_pellets_heavy") ICE_PELLETS_HEAVY,
    @SerializedName("ice_pellets") ICE_PELLETS,
    @SerializedName("ice_pellets_light") ICE_PELLETS_LIGHT,
    @SerializedName("snow_heavy") SNOW_HEAVY,
    @SerializedName("snow") SNOW,
    @SerializedName("snow_light") SNOW_LIGHT,
    @SerializedName("flurries") FLURRIES,
    @SerializedName("tstorm") TSTORM,
    @SerializedName("rain_heavy") RAIN_HEAVY,
    @SerializedName("rain") RAIN,
    @SerializedName("rain_light") RAIN_LIGHT,
    @SerializedName("drizzle") DRIZZLE,
    @SerializedName("fog_light") FOG_LIGHT,
    @SerializedName("fog") FOG,
    @SerializedName("cloudy") CLOUDY,
    @SerializedName("mostly_cloudy") MOSTLY_CLOUDY,
    @SerializedName("partly_cloudy") PARTLY_CLOUDY,
    @SerializedName("mostly_clear") MOSTLY_CLEAR,
    @SerializedName("clear") CLEAR;

    fun getWeatherString(context: Context): String {
        return when(this) {
            FREEZING_RAIN_HEAVY -> context.getString(R.string.freezing_rain_heavy)
            FREEZING_RAIN -> context.getString(R.string.freezing_rain)
            FREEZING_RAIN_LIGHT -> context.getString(R.string.freezing_rain_light)
            FREEZING_DRIZZLE -> context.getString(R.string.freezing_drizzle)
            ICE_PELLETS_HEAVY -> context.getString(R.string.ice_pellets_heavy)
            ICE_PELLETS -> context.getString(R.string.ice_pellets)
            ICE_PELLETS_LIGHT -> context.getString(R.string.ice_pellets_light)
            SNOW_HEAVY -> context.getString(R.string.snow_heavy)
            SNOW -> context.getString(R.string.snow)
            SNOW_LIGHT -> context.getString(R.string.snow_light)
            FLURRIES -> context.getString(R.string.flurries)
            TSTORM -> context.getString(R.string.tstorm)
            RAIN_HEAVY -> context.getString(R.string.rain_heavy)
            RAIN -> context.getString(R.string.rain)
            RAIN_LIGHT -> context.getString(R.string.rain_light)
            DRIZZLE -> context.getString(R.string.drizzle)
            FOG_LIGHT -> context.getString(R.string.fog_light)
            FOG -> context.getString(R.string.fog)
            CLOUDY -> context.getString(R.string.cloudy)
            MOSTLY_CLOUDY -> context.getString(R.string.mostly_cloudy)
            PARTLY_CLOUDY -> context.getString(R.string.partly_cloudy)
            MOSTLY_CLEAR -> context.getString(R.string.mostly_clear)
            CLEAR -> context.getString(R.string.clear)
        }
    }
}
