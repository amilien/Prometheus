package com.amilien.prometheus.di

import android.content.Context
import com.amilien.prometheus.model.api.ApiService
import com.amilien.prometheus.repository.LocationRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideLocationRepository(context: Context, apiService: ApiService) = LocationRepository(context, apiService)
}
