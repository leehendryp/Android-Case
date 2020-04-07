package com.leehendryp.androidcase.dataentry.data.remote

import com.leehendryp.androidcase.core.apis.GeoApi
import com.leehendryp.androidcase.core.apis.TicTacApi
import com.leehendryp.androidcase.core.utils.coTryCatch
import com.leehendryp.androidcase.dataentry.data.CouldNotFetchAnttPrices
import com.leehendryp.androidcase.dataentry.data.CouldNotFetchRouteDetails
import com.leehendryp.androidcase.dataentry.data.entities.request.InfoForAntt
import com.leehendryp.androidcase.dataentry.data.entities.request.InfoProvidedByDriver
import com.leehendryp.androidcase.dataentry.data.entities.response.AnttPricesResponse
import com.leehendryp.androidcase.dataentry.data.entities.response.RouteDetailsResponse
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val geoApi: GeoApi,
    private val ticTacApi: TicTacApi
) : RemoteDataSource {
    companion object {
        private const val REMOTE_SOURCE_ERROR =
            "Could not fetch data from remote data source"
    }

    override suspend fun getRouteDetailsFrom(info: InfoProvidedByDriver): RouteDetailsResponse = coTryCatch(
        { geoApi.send(info) },
        { CouldNotFetchRouteDetails(REMOTE_SOURCE_ERROR, it) }
    )

    override suspend fun getAnttPrices(infoForAntt: InfoForAntt): AnttPricesResponse = coTryCatch(
        { ticTacApi.send(infoForAntt) },
        { CouldNotFetchAnttPrices(REMOTE_SOURCE_ERROR, it) }
    )
}