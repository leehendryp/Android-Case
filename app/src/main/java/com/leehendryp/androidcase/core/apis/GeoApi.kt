package com.leehendryp.androidcase.core.apis

import com.leehendryp.androidcase.dataentry.data.entities.request.InfoProvidedByDriver
import com.leehendryp.androidcase.dataentry.data.entities.response.RouteDetailsResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface GeoApi {
    companion object {
        const val GEO_API_BASE_URL = "https://geo.api.truckpad.io/v1/"
        private const val ENDPOINT = "route"
    }

    @POST(ENDPOINT)
    suspend fun send(@Body info: InfoProvidedByDriver): RouteDetailsResponse
}