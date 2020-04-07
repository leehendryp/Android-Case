package com.leehendryp.androidcase.dataentry.domain

import com.google.android.libraries.places.api.model.Place
import com.leehendryp.androidcase.dataentry.data.entities.response.AnttPricesResponse
import com.leehendryp.androidcase.dataentry.data.entities.response.PointResponse
import com.leehendryp.androidcase.dataentry.data.entities.response.RouteDetailsResponse

fun RouteDetailsResponse.toRouteWithAnttPrices(anttPrices: AnttPricesResponse) =
    RouteWithAnttPrices(
        distance = distance ?: 0,
        distanceUnit = distanceUnit ?: "",
        duration = duration ?: 0,
        durationUnit = durationUnit ?: "",
        points = points?.toPointList() ?: listOf(),
        provider = provider ?: "",
        route = route ?: listOf(),
        tollCost = tollCost ?: 0,
        tollCostUnit = tollCostUnit ?: "",
        tollCount = tollCount ?: 0,
        totalCost = totalCost ?: 0.0,
        cached = cached ?: false,
        fuelUsage = fuelUsage ?: 0.0,
        fuelUsageUnit = fuelUsageUnit ?: "",
        fuelCost = fuelCost ?: 0.0,
        fuelCostUnit = fuelCostUnit ?: "",
        frigorificada = anttPrices.frigorificada ?: 0.0,
        geral = anttPrices.geral ?: 0.0,
        granel = anttPrices.granel ?: 0.0,
        neogranel = anttPrices.neogranel ?: 0.0,
        perigosa = anttPrices.perigosa ?: 0.0
    )

fun List<PointResponse>.toPointList(): List<Point> {
    val list = mutableListOf<Point>()
    forEach { response -> list.add(response.toPoint()) }
    return list
}

fun PointResponse.toPoint() = Point(point = point ?: listOf())

fun Place.toAddress() = Address(
    name = name ?: "",
    formattedAddress = address ?: "",
    location = Location(
        latitude = latLng?.latitude ?: 0.0,
        longitude = latLng?.longitude ?: 0.0
    )
)