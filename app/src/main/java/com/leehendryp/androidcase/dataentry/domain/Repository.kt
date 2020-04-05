package com.leehendryp.androidcase.dataentry.domain

import com.leehendryp.androidcase.dataentry.data.entities.request.InfoProvidedByDriver

interface Repository {
    suspend fun getRouteWithAnttPricesFrom(info: InfoProvidedByDriver): RouteWithAnttPrices
    suspend fun save(routeWithAnttPrices: RouteWithAnttPrices)
    suspend fun getHistory(): Set<RouteWithAnttPrices>
}