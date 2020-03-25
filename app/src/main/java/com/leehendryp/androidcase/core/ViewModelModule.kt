package com.leehendryp.androidcase.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leehendryp.androidcase.dataentry.DataEntryViewModel
import com.leehendryp.androidcase.map.RouteViewModel
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
    @ViewModelKey(RouteViewModel::class)
    abstract fun bindRouteViewModel(viewModel: RouteViewModel): ViewModel
}