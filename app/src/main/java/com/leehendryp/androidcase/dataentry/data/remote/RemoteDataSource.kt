package com.leehendryp.androidcase.dataentry.data.remote

import com.leehendryp.androidcase.dataentry.data.request.InfoForAntt
import com.leehendryp.androidcase.dataentry.data.request.InfoProvidedByDriver
import com.leehendryp.androidcase.dataentry.data.response.AnttPrices
import com.leehendryp.androidcase.dataentry.data.response.Route

interface RemoteDataSource {
    suspend fun send(info: InfoProvidedByDriver): Route
    suspend fun getAnttPrices(infoForAntt: InfoForAntt): AnttPrices
}