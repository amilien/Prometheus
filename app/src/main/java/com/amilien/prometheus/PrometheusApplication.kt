package com.amilien.prometheus

import android.app.Application
import com.amilien.prometheus.di.AppComponent
import com.amilien.prometheus.di.DaggerAppComponent

class PrometheusApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .application(this)
            .build()
        appComponent.inject(this)
        instance = this
    }

    companion object {

        private lateinit var instance: PrometheusApplication

        lateinit var appComponent: AppComponent
    }
}
