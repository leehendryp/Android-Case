package com.leehendryp.androidcase.dataentry.domain

import com.leehendryp.androidcase.dataentry.data.request.InfoProvidedByDriver

interface DataEntryRepository {
    suspend fun send(info: InfoProvidedByDriver): RouteWithAnttPrices
}