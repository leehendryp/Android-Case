package com.leehendryp.androidcase.dataentry.domain

import com.leehendryp.androidcase.dataentry.data.response.AnttPrices
import com.leehendryp.androidcase.dataentry.data.response.Route

fun toRouteWithAnttPricesMapper(route: Route, anttPrices: AnttPrices) = RouteWithAnttPrices(
    distance = route.distance ?: 0,
    distanceUnit = route.distanceUnit ?: "",
    duration = route.duration ?: 0,
    durationUnit = route.durationUnit ?: "",
    points = route.points ?: listOf(),
    provider = route.provider ?: "",
    route = route.route ?: listOf(),
    tollCost = route.tollCost ?: 0,
    tollCostUnit = route.tollCostUnit ?: "",
    tollCount = route.tollCount ?: 0,
    totalCost = route.totalCost ?: 0.0,
    frigorificada = anttPrices.frigorificada ?: 0.0,
    geral = anttPrices.geral ?: 0.0,
    granel = anttPrices.granel ?: 0.0,
    neogranel = anttPrices.neogranel ?: 0.0,
    perigosa = anttPrices.perigosa ?: 0.0
)