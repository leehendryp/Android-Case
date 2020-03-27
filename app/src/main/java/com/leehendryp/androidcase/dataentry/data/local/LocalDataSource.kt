package com.leehendryp.androidcase.dataentry.data.local

import com.leehendryp.androidcase.dataentry.domain.RouteWithAnttPrices

interface LocalDataSource {
    suspend fun save(routeWithAnttPrices: RouteWithAnttPrices)
    suspend fun getHistory(): Set<RouteWithAnttPrices>
}