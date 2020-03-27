package com.leehendryp.androidcase.dataentry.data.remote

import com.leehendryp.androidcase.core.GeoApi
import com.leehendryp.androidcase.core.TicTacApi
import com.leehendryp.androidcase.core.utils.coTryCatch
import com.leehendryp.androidcase.dataentry.data.CouldNotFetchAnttPrices
import com.leehendryp.androidcase.dataentry.data.CouldNotFetchRouteDetails
import com.leehendryp.androidcase.dataentry.data.entities.request.InfoForAntt
import com.leehendryp.androidcase.dataentry.data.entities.request.InfoProvidedByDriver
import com.leehendryp.androidcase.dataentry.data.entities.response.AnttPrices
import com.leehendryp.androidcase.dataentry.data.entities.response.RouteDetails

class RemoteDataSourceImpl(
    private val geoApi: GeoApi,
    private val ticTacApi: TicTacApi
) : RemoteDataSource {
    companion object {
        private const val REMOTE_SOURCE_ERROR =
            "Could not fetch data from remote data source"
    }

    override suspend fun getRouteDetailsFrom(info: InfoProvidedByDriver): RouteDetails = coTryCatch(
        { geoApi.send(info) },
        { CouldNotFetchRouteDetails(REMOTE_SOURCE_ERROR, it) }
    )

    override suspend fun getAnttPrices(infoForAntt: InfoForAntt): AnttPrices = coTryCatch(
        { ticTacApi.getAnttPrices(infoForAntt) },
        { CouldNotFetchAnttPrices(REMOTE_SOURCE_ERROR, it) }
    )
}