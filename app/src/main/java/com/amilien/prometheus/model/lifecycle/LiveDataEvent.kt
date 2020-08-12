package com.amilien.prometheus.model.lifecycle

/**
 * Used for dispatching live data events, and can contain some data or an error.
 */

open class LiveDataEvent<T>(
    val data: T?,
    private val error: Throwable? = null
) {

    val requireData
        get() = data ?: throw IllegalArgumentException("Data is null")

    val requireError: Throwable
        get() = requireNotNull(error) { "Error is null" }

    constructor(error: Throwable?) : this(null, error)

    fun hasData() = data != null

    fun hasError() = error != null
}
