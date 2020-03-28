package com.leehendryp.androidcase.dataentry.data

import com.leehendryp.androidcase.dataentry.data.entities.request.InfoForAntt
import com.leehendryp.androidcase.dataentry.data.entities.request.InfoProvidedByDriver
import com.leehendryp.androidcase.dataentry.data.entities.request.Place
import com.leehendryp.androidcase.dataentry.data.entities.response.AnttPrices
import com.leehendryp.androidcase.dataentry.data.entities.response.Point
import com.leehendryp.androidcase.dataentry.data.entities.response.RouteDetails
import com.leehendryp.androidcase.dataentry.domain.RouteWithAnttPrices

object DTOs {
    val infoProvidedByDriver = InfoProvidedByDriver(
        fuelConsumption = 5,
        fuelPrice = 4.4,
        places = listOf(
            Place(listOf(-46.68664, -23.59496)),
            Place(listOf(-46.67678, -23.59867))
        ),
        shafts = 3
    )

    val routeDetails = RouteDetails(
        points = listOf(
            Point(listOf(-46.68664, -23.59496), "Provided"),
            Point(listOf(-46.67678, -23.59867), "Provided")
        ),
        distance = 1962,
        distanceUnit = "meters",
        duration = 583,
        durationUnit = "seconds",
        hasTolls = false,
        tollCount = 0,
        tollCost = 0,
        tollCostUnit = "R$",
        route = listOf(
            listOf(
                listOf(-46.68662, -23.59504),
                listOf(-46.6881, -23.59571),
                listOf(-46.68701, -23.59613),
                listOf(-46.68546, -23.59575),
                listOf(-46.67528, -23.60051),
                listOf(-46.67486, -23.59966),
                listOf(-46.6768, -23.59871)
            )
        ),
        provider = "Maplink",
        cached = true,
        fuelUsage = 0.39,
        fuelUsageUnit = "liters",
        fuelCost = 1.73,
        fuelCostUnit = "R$",
        totalCost = 1.73
    )

    val infoForAntt = InfoForAntt(
        axis = 3,
        distance = 1962.toDouble(),
        hasReturnShipment = true
    )

    val anttPrices = AnttPrices(
        frigorificada = 3987.96,
        geral = 5654.57,
        granel = 5595.04,
        neogranel = 5059.35,
        perigosa = 3571.3
    )

    val routeWithAnttPrices = RouteWithAnttPrices(
        points = listOf(
            Point(listOf(-46.68664, -23.59496), "Provided"),
            Point(listOf(-46.67678, -23.59867), "Provided")
        ),
        distance = 1962,
        distanceUnit = "meters",
        duration = 583,
        durationUnit = "seconds",
        tollCount = 0,
        tollCost = 0,
        tollCostUnit = "R$",
        route = listOf(
            listOf(
                listOf(-46.68662, -23.59504),
                listOf(-46.6881, -23.59571),
                listOf(-46.68701, -23.59613),
                listOf(-46.68546, -23.59575),
                listOf(-46.67528, -23.60051),
                listOf(-46.67486, -23.59966),
                listOf(-46.6768, -23.59871)
            )
        ),
        provider = "Maplink",
        cached = true,
        fuelUsage = 0.39,
        fuelUsageUnit = "liters",
        fuelCost = 1.73,
        fuelCostUnit = "R$",
        totalCost = 1.73,
        frigorificada = 3987.96,
        geral = 5654.57,
        granel = 5595.04,
        neogranel = 5059.35,
        perigosa = 3571.3
    )
}