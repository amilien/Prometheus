package com.amilien.prometheus.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amilien.prometheus.di.qualifier.ViewModelKey
import com.amilien.prometheus.viewmodel.FeedViewModel
import com.amilien.prometheus.viewmodel.MapsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MapsViewModel::class)
    abstract fun bindMapsViewModel(viewModel: MapsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    abstract fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel
}
