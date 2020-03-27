package com.leehendryp.androidcase.core

import com.leehendryp.androidcase.dataentry.data.entities.request.InfoProvidedByDriver
import com.leehendryp.androidcase.dataentry.data.entities.response.RouteDetails
import retrofit2.http.Body
import retrofit2.http.POST

interface GeoApi {
    companion object {
        private const val ENDPOINT = "route/"
    }

    @POST(ENDPOINT)
    suspend fun send(@Body info: InfoProvidedByDriver): RouteDetails
}