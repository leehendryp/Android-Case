package com.leehendryp.androidcase.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leehendryp.androidcase.dataentry.presentation.DataEntryViewModel
import com.leehendryp.androidcase.map.RouteDetailsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DataEntryViewModel::class)
    abstract fun bindDataEntryViewModel(viewModel: DataEntryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RouteDetailsViewModel::class)
    abstract fun bindRouteDetailsViewModel(viewModel: RouteDetailsViewModel): ViewModel
}