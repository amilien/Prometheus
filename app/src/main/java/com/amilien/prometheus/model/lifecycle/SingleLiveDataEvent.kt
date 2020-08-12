package com.amilien.prometheus.model.lifecycle

/**
 * [SingleLiveEvent] extension that wraps [LiveDataEvent] with generic parameter.
 */

class SingleLiveDataEvent<T> : SingleLiveEvent<LiveDataEvent<T>>()