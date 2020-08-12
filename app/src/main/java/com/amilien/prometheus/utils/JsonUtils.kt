package com.amilien.prometheus.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

val gson: Gson = GsonBuilder().create()

inline fun <reified T> fromJson(json: String): T = gson.fromJson(json, object : TypeToken<T>() {}.type)

inline fun <reified T> toJson(jsonElement: T): String = gson.toJson(jsonElement)

inline fun <reified T> SharedPreferences.putJsonString(data: T, key: String) {
    edit { putString(key, toJson(data)).commit() }
}

inline fun <reified T> SharedPreferences.fromJsonString(key: String): T? {
    return getString(key, null)?.let { fromJson<T>(it) }
}
