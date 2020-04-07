package com.leehendryp.androidcase.dataentry.domain

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class RouteWithAnttPrices(
    @SerializedName("distance") val distance: Int,
    @SerializedName("distance_unit") val distanceUnit: String,
    @SerializedName("duration") val duration: Int,
    @SerializedName("duration_unit") val durationUnit: String,
    @SerializedName("points") val points: @RawValue List<Point>,
    @SerializedName("provider") val provider: String,
    @SerializedName("route") val route: List<List<List<Double>>>,
    @SerializedName("toll_cost") val tollCost: Int,
    @SerializedName("toll_cost_unit") val tollCostUnit: String,
    @SerializedName("toll_count") val tollCount: Int,
    @SerializedName("total_cost") val totalCost: Double,
    @SerializedName("cached") val cached: Boolean? = false,
    @SerializedName("fuel_cost") val fuelCost: Double?,
    @SerializedName("fuel_cost_unit") val fuelCostUnit: String?,
    @SerializedName("fuel_usage") val fuelUsage: Double?,
    @SerializedName("fuel_usage_unit") val fuelUsageUnit: String?,
    @SerializedName("frigorificada") val frigorificada: Double,
    @SerializedName("geral") val geral: Double,
    @SerializedName("granel") val granel: Double,
    @SerializedName("neogranel") val neogranel: Double,
    @SerializedName("perigosa") val perigosa: Double
) : Parcelable