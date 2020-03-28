package com.leehendryp.androidcase.dataentry.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leehendryp.androidcase.dataentry.data.entities.request.InfoProvidedByDriver
import com.leehendryp.androidcase.dataentry.domain.Repository
import com.leehendryp.androidcase.dataentry.domain.RouteWithAnttPrices
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class DataEntryViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    companion object {
        const val MIN_SHAFTS = 2
    }

    private val _state by lazy { MutableLiveData<DataEntryState>().apply { toDefault() } }
    val state: LiveData<DataEntryState> = _state

    private val _data by lazy { MutableLiveData<RouteWithAnttPrices>() }
    val data: LiveData<RouteWithAnttPrices> = _data

    fun getRouteDetailsFrom(info: InfoProvidedByDriver): Job = launchDataLoad {
        _data.postValue(repository.getRouteDetailsFrom(info))
        _state.toSuccess()
    }

    private fun <T> launchDataLoad(block: suspend () -> T): Job {
        _state.toLoading()
        return viewModelScope.launch {
            try {
                block()
            } catch (error: Throwable) {
                _state.toError(error)
            } finally {
                _state.toDefault()
            }
        }
    }
}