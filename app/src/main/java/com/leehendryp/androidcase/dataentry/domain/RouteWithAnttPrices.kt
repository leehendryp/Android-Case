package com.leehendryp.androidcase.dataentry.domain

import com.google.gson.annotations.SerializedName
import com.leehendryp.androidcase.dataentry.data.response.Point

data class RouteWithAnttPrices(
    @SerializedName("distance") val distance: Int,
    @SerializedName("distance_unit") val distanceUnit: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("duration_unit") val durationUnit: String,
    @SerializedName("points") val points: List<Point>,
    @SerializedName("provider") val provider: String,
    @SerializedName("route") val route: List<List<Point>>,
    @SerializedName("toll_cost") val tollCost: Int,
    @SerializedName("toll_cost_unit") val tollCostUnit: String,
    @SerializedName("toll_count") val tollCount: Int,
    @SerializedName("total_cost") val totalCost: Double,
    @SerializedName("frigorificada") val frigorificada: Double,
    @SerializedName("geral") val geral: Double,
    @SerializedName("granel") val granel: Double,
    @SerializedName("neogranel") val neogranel: Double,
    @SerializedName("perigosa") val perigosa: Double
)