package com.leehendryp.androidcase.core.apis

import com.leehendryp.androidcase.dataentry.data.entities.request.InfoForAntt
import com.leehendryp.androidcase.dataentry.data.entities.response.AnttPrices
import retrofit2.http.Body
import retrofit2.http.POST

interface TicTacApi {
    companion object {
        const val TIC_TAC_API_BASE_URL = "https://tictac.api.truckpad.io/v1/"
        private const val ENDPOINT = "antt_price/all"
    }

    @POST(ENDPOINT)
    suspend fun send(@Body infoForAntt: InfoForAntt): AnttPrices
}