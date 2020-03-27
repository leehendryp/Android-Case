package com.leehendryp.androidcase.dataentry.domain

import com.leehendryp.androidcase.dataentry.data.entities.response.AnttPrices
import com.leehendryp.androidcase.dataentry.data.entities.response.RouteDetails

fun createRouteWithAnttPrices(routeDetails: RouteDetails, anttPrices: AnttPrices) =
    RouteWithAnttPrices(
        distance = routeDetails.distance ?: 0,
        distanceUnit = routeDetails.distanceUnit ?: "",
        duration = routeDetails.duration ?: 0,
        durationUnit = routeDetails.durationUnit ?: "",
        points = routeDetails.points ?: listOf(),
        provider = routeDetails.provider ?: "",
        route = routeDetails.route ?: listOf(),
        tollCost = routeDetails.tollCost ?: 0,
        tollCostUnit = routeDetails.tollCostUnit ?: "",
        tollCount = routeDetails.tollCount ?: 0,
        totalCost = routeDetails.totalCost ?: 0.0,
        cached = routeDetails.cached ?: false,
        fuelUsage = routeDetails.fuelUsage ?: 0.0,
        fuelUsageUnit = routeDetails.fuelUsageUnit ?: "",
        fuelCost = routeDetails.fuelCost ?: 0.0,
        fuelCostUnit = routeDetails.fuelCostUnit ?: "",
        frigorificada = anttPrices.frigorificada ?: 0.0,
        geral = anttPrices.geral ?: 0.0,
        granel = anttPrices.granel ?: 0.0,
        neogranel = anttPrices.neogranel ?: 0.0,
        perigosa = anttPrices.perigosa ?: 0.0
    )