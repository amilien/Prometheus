package com.amilien.prometheus.utils

import androidx.lifecycle.MutableLiveData
import com.amilien.prometheus.model.lifecycle.LiveDataEvent
import com.amilien.prometheus.model.lifecycle.SingleLiveDataEvent
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <T> CoroutineScope.launch(
    dataLiveData: MutableLiveData<LiveDataEvent<T>>? = null,
    loadingLiveData: SingleLiveDataEvent<Boolean>? = null,
    doWithError: (Throwable) -> Unit = {},
    doWithData: (LiveDataEvent<T>) -> Unit = {},
    context: CoroutineContext = EmptyCoroutineContext,
    doGetData: suspend () -> T
): Job {
    return launch(context) {
        coroutineBody(dataLiveData, loadingLiveData, doWithError, doWithData, doGetData)
    }
}

private suspend fun <T> coroutineBody(
    dataLiveData: MutableLiveData<LiveDataEvent<T>>?,
    loadingLiveData: SingleLiveDataEvent<Boolean>?,
    doWithError: (Throwable) -> Unit,
    doWithData: (LiveDataEvent<T>) -> Unit,
    doGetData: suspend () -> T
) {
    if (loadingLiveData?.value?.requireData != true) {
        loadingLiveData?.value = LiveDataEvent(true)
    }
    val event: LiveDataEvent<T> = try {
        LiveDataEvent(doGetData()).also { doWithData(it) }
    } catch (e: CancellationException) {
        doWithError(e)
        return
    } catch (e: Exception) {
        doWithError(e)
        LiveDataEvent(e)
    }
    dataLiveData?.value = event
    loadingLiveData?.value = LiveDataEvent(false)
}
