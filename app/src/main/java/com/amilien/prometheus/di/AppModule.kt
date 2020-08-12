package com.amilien.prometheus.di

import android.app.Application
import android.content.Context
import com.amilien.prometheus.model.data.Preferences
import com.amilien.prometheus.utils.PREFERENCE_FILE_MODEL
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideAppContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @Singleton
    fun providePreferences(context: Context): Preferences {
        val sharedPreferences = context.getSharedPreferences(PREFERENCE_FILE_MODEL, Application.MODE_PRIVATE)
        return Preferences(sharedPreferences)
    }
}
