package com.leehendryp.androidcase.dataentry.data.remote

import com.leehendryp.androidcase.dataentry.data.entities.request.InfoForAntt
import com.leehendryp.androidcase.dataentry.data.entities.request.InfoProvidedByDriver
import com.leehendryp.androidcase.dataentry.data.entities.response.AnttPricesResponse
import com.leehendryp.androidcase.dataentry.data.entities.response.RouteDetailsResponse

interface RemoteDataSource {
    suspend fun getRouteDetailsFrom(info: InfoProvidedByDriver): RouteDetailsResponse
    suspend fun getAnttPrices(infoForAntt: InfoForAntt): AnttPricesResponse
}