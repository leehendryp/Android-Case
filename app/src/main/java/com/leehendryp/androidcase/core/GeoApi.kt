package com.leehendryp.androidcase.core

import com.leehendryp.androidcase.request.InfoProvidedByDriver
import com.leehendryp.androidcase.response.Route
import retrofit2.http.POST

interface GeoApi {
    companion object {
        private const val ENDPOINT = "route/"
    }

    @POST(ENDPOINT)
    suspend fun send(info: InfoProvidedByDriver): Route
}