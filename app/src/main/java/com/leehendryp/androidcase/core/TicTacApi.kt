package com.leehendryp.androidcase.core

import com.leehendryp.androidcase.dataentry.data.entities.request.InfoForAntt
import com.leehendryp.androidcase.dataentry.data.entities.response.AnttPrices
import retrofit2.http.Body
import retrofit2.http.POST

interface TicTacApi {
    companion object {
        private const val ENDPOINT = "all/"
    }

    @POST(ENDPOINT)
    suspend fun getAnttPrices(@Body infoForAntt: InfoForAntt): AnttPrices
}