package com.leehendryp.androidcase.core.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.leehendryp.androidcase.dataentry.presentation.DataEntryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(DataEntryViewModel::class)
    abstract fun bindDataEntryViewModel(viewModel: DataEntryViewModel): ViewModel

//    @Binds
//    @IntoMap
//    @ViewModelKey(RouteDetailsViewModel::class)
//    abstract fun bindRouteDetailsViewModel(viewModel: RouteDetailsViewModel): ViewModel
}