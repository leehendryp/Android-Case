package com.leehendryp.androidcase.core

import com.leehendryp.androidcase.dataentry.data.request.InfoForAntt
import com.leehendryp.androidcase.dataentry.data.response.AnttPrices
import retrofit2.http.POST

interface TicTacApi {
    companion object {
        private const val ENDPOINT = "all/"
    }

    @POST(ENDPOINT)
    suspend fun getAnttPrices(infoForAntt: InfoForAntt): AnttPrices
}