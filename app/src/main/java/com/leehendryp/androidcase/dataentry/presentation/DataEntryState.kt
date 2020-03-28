package com.leehendryp.androidcase.dataentry.presentation

import androidx.lifecycle.MutableLiveData
import com.leehendryp.androidcase.dataentry.domain.RouteWithAnttPrices
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Default
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Error
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Loading
import com.leehendryp.androidcase.dataentry.presentation.DataEntryState.Success

sealed class DataEntryState {
    object Default : DataEntryState()
    object Loading : DataEntryState()
    data class Success(val data: RouteWithAnttPrices) : DataEntryState()
    data class Error(val error: Throwable) : DataEntryState()
}

fun MutableLiveData<DataEntryState>.toDefault() {
    value = Default
}

fun MutableLiveData<DataEntryState>.toLoading() {
    value = Loading
}

fun MutableLiveData<DataEntryState>.toSuccess(data: RouteWithAnttPrices) = postValue(Success(data))

fun MutableLiveData<DataEntryState>.toError(error: Throwable) = postValue(Error(error))