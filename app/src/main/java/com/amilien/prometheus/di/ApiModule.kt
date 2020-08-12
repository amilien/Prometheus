package com.amilien.prometheus.di

import com.amilien.prometheus.BuildConfig
import com.amilien.prometheus.di.qualifier.ApiHttpClient
import com.amilien.prometheus.model.api.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {

    private fun OkHttpClient.Builder.addLoggingInterceptor(): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            addInterceptor(logging)
        }
        return this
    }

    @Provides
    @Singleton
    @ApiHttpClient
    fun provideApiHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(CONNECT_TIMEOUT, TIME_UNIT)
            .readTimeout(READ_TIMEOUT, TIME_UNIT)
            .addLoggingInterceptor()
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideApiService(@ApiHttpClient httpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl("https://api.climacell.co/")
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    companion object {

        private const val CONNECT_TIMEOUT = 30L
        private const val READ_TIMEOUT = 30L
        private val TIME_UNIT = TimeUnit.SECONDS
    }
}
