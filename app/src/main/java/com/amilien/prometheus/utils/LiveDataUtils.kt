package com.amilien.prometheus.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.amilien.prometheus.model.lifecycle.LiveDataEvent
import com.amilien.prometheus.model.lifecycle.SingleLiveDataEvent

inline fun <T> SingleLiveDataEvent<T>.createObserverWithError(
    lifecycleOwner: LifecycleOwner,
    crossinline doOnError: (Throwable) -> Unit = {},
    crossinline doWithData: (LiveDataEvent<T>) -> Unit
) {
    observe(lifecycleOwner, getObserver(doOnError, doWithData))
}

inline fun <T> getObserver(
    crossinline doOnError: (Throwable) -> Unit = {},
    crossinline doWithData: (LiveDataEvent<T>) -> Unit
) = Observer<LiveDataEvent<T>> {
    if (it == null || it.hasError() || !it.hasData()) {
        doOnError(it.requireError)
    } else {
        doWithData(it)
    }
}
