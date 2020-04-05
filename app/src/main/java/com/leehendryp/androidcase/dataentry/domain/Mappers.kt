package com.leehendryp.androidcase.dataentry.domain

import com.google.android.libraries.places.api.model.Place
import com.leehendryp.androidcase.dataentry.data.entities.response.AnttPrices
import com.leehendryp.androidcase.dataentry.data.entities.response.RouteDetails

fun RouteDetails.mapIntoRouteWithAnttPrices(anttPrices: AnttPrices) =
    RouteWithAnttPrices(
        distance = distance ?: 0,
        distanceUnit = distanceUnit ?: "",
        duration = duration ?: 0,
        durationUnit = durationUnit ?: "",
        points = points ?: listOf(),
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

fun Place.mapIntoAddress() = Address(
    name = name ?: "",
    formattedAddress = address ?: "",
    location = Location(
        latitude = latLng?.latitude ?: 0.0,
        longitude = latLng?.longitude ?: 0.0
    )
)