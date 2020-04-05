package com.leehendryp.androidcase.core.di

import android.content.Context
import com.leehendryp.androidcase.dataentry.data.di.DataModule
import com.leehendryp.androidcase.dataentry.presentation.DataEntryFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(dataEntryFragment: DataEntryFragment)
}