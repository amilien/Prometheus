package com.amilien.prometheus.view

import android.content.Context
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amilien.prometheus.PrometheusApplication
import javax.inject.Inject

abstract class BaseFragment(layoutId: Int) : Fragment(layoutId) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        PrometheusApplication.appComponent.inject(this)
        super.onAttach(context)
    }

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.createViewModel(
        crossinline factoryProducer: (() -> ViewModelProvider.Factory) = { viewModelFactory }
    ) = createViewModelLazy(VM::class, { viewModelStore }, { factoryProducer() })

    @MainThread
    inline fun <reified VM : ViewModel> Fragment.createActivityViewModel() =
        createViewModelLazy(VM::class, { requireActivity().viewModelStore }, { viewModelFactory })
}
