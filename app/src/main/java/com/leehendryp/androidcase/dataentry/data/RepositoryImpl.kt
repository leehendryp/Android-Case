package com.leehendryp.androidcase.dataentry.data

import com.leehendryp.androidcase.core.utils.coTryCatch
import com.leehendryp.androidcase.dataentry.data.entities.request.InfoForAntt
import com.leehendryp.androidcase.dataentry.data.entities.request.InfoProvidedByDriver
import com.leehendryp.androidcase.dataentry.data.local.LocalDataSource
import com.leehendryp.androidcase.dataentry.data.remote.RemoteDataSource
import com.leehendryp.androidcase.dataentry.domain.Repository
import com.leehendryp.androidcase.dataentry.domain.RouteWithAnttPrices
import com.leehendryp.androidcase.dataentry.domain.toRouteWithAnttPrices
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val remoteSource: RemoteDataSource,
    private val localSource: LocalDataSource
) : Repository {
    override suspend fun getRouteWithAnttPricesFrom(info: InfoProvidedByDriver): RouteWithAnttPrices =
        coTryCatch {
            val routeDetails = remoteSource.getRouteDetailsFrom(info)
            val anttPrices = remoteSource.getAnttPrices(
                InfoForAntt(
                    shafts = info.shafts,
                    distance = routeDetails.distance?.toDouble() ?: 0.0,
                    hasReturnShipment = true
                )
            )
            routeDetails.toRouteWithAnttPrices(anttPrices).also { save(it) }
        }

    override suspend fun save(routeWithAnttPrices: RouteWithAnttPrices) = coTryCatch {
        localSource.save(routeWithAnttPrices)
    }

    override suspend fun getHistory(): Set<RouteWithAnttPrices> = coTryCatch {
        localSource.getHistory()
    }
}