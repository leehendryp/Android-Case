package com.leehendryp.androidcase.dataentry.data.local

import com.leehendryp.androidcase.dataentry.domain.RouteWithAnttPrices

class LocalDataSourceImpl : LocalDataSource {
    override suspend fun save(routeWithAnttPrices: RouteWithAnttPrices) = Unit

    override suspend fun getHistory(): Set<RouteWithAnttPrices> {
        return setOf()
    }
}