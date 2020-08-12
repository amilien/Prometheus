package com.amilien.prometheus.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission
import retrofit2.Call
import retrofit2.Response
import retrofit2.awaitResponse
import java.io.IOException

const val EMPTY_API_ERROR = "Empty API error"

suspend fun <T : Any> Call<T>.checkNetworkAndAwaitResponse(context: Context): Response<T> {
    return if (context.isNetworkConnected()) {
        awaitResponse()
    } else {
        throw IOException()
    }
}

inline fun <reified DataModel> Response<DataModel>.handleResponse(): DataModel {
    return when {
        DataModel::class.java == Unit::class.java && !isSuccessful -> resolveException()
        DataModel::class.java == Unit::class.java -> Unit as DataModel
        isSuccessful && body() != null -> body() as DataModel
        else -> resolveException()
    }
}

fun <DataModel> Response<DataModel>.resolveException(): DataModel {
    val error = errorBody()?.string()
    throw when (error) {
        null -> IllegalStateException(EMPTY_API_ERROR)
        else -> Exception(error)
    }
}

@Suppress("DEPRECATION")
@RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
private fun Context.isNetworkConnected(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || activeNetwork.hasTransport(
                NetworkCapabilities.TRANSPORT_CELLULAR
            ) ||
                    activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.activeNetworkInfo?.run {
            return when (type) {
                ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE, ConnectivityManager.TYPE_ETHERNET -> true
                else -> false
            }
        } ?: return false
    }
}
