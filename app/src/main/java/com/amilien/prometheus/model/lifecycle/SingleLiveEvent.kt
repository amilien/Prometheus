package com.amilien.prometheus.model.lifecycle

import androidx.annotation.MainThread
import androidx.annotation.Nullable
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 *
 *
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 *
 *
 * Note that all observers are going to be notified of changes.
 */

open class SingleLiveEvent<T> : MutableLiveData<T>() {

    private val pendingObservers =
        ConcurrentHashMap<Observer<T>, Pair<Observer<in T>, AtomicBoolean>>()

    @MainThread
    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        val interceptor = object : Observer<T> {
            override fun onChanged(t: T?) {
                var observerToTrigger: Observer<in T>? = null
                synchronized(pendingObservers) {
                    pendingObservers[this]?.let {
                        if (pendingObservers.containsKey(this) &&
                            it.second.compareAndSet(true, false)
                        ) {
                            observerToTrigger = it.first
                        }
                    }
                }
                observerToTrigger?.onChanged(t)
            }
        }

        synchronized(pendingObservers) {
            pendingObservers.put(interceptor, observer to AtomicBoolean(false))
        }

        // observe the internal MutableLiveData
        super.observe(owner, interceptor)
    }

    override fun removeObserver(observer: Observer<in T>) = with(pendingObservers) {
        synchronized(this) {
            filter { (key, value) ->
                observer == key || observer == value.first
            }.keys.firstOrNull()?.let {
                remove(it)
            }
        }
        super.removeObserver(observer)
    }

    @MainThread
    override fun setValue(@Nullable t: T?) = with(pendingObservers) {
        synchronized(this) {
            values.forEach { it.second.set(true) }
        }
        super.setValue(t)
    }

    fun flush() {
        value = null
    }
}
