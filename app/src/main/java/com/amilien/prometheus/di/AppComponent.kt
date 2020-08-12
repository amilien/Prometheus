package com.amilien.prometheus.di

import android.app.Application
import com.amilien.prometheus.PrometheusApplication
import com.amilien.prometheus.view.BaseFragment
import com.amilien.prometheus.view.feed.FeedFragment
import com.amilien.prometheus.view.maps.MapsFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, ViewModelModule::class, ApiModule::class, RepositoryModule::class])
interface AppComponent {

    fun inject(application: PrometheusApplication)
    fun inject(fragment: BaseFragment)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}
