package com.leehendryp.androidcase.dataentry.data.remote

import com.leehendryp.androidcase.dataentry.data.entities.request.InfoForAntt
import com.leehendryp.androidcase.dataentry.data.entities.request.InfoProvidedByDriver
import com.leehendryp.androidcase.dataentry.data.entities.response.AnttPrices
import com.leehendryp.androidcase.dataentry.data.entities.response.RouteDetails

interface RemoteDataSource {
    suspend fun getRouteDetailsFrom(info: InfoProvidedByDriver): RouteDetails
    suspend fun getAnttPrices(infoForAntt: InfoForAntt): AnttPrices
}