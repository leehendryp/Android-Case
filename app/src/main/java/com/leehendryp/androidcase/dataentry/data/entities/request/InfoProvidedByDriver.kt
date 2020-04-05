package com.leehendryp.androidcase.dataentry.data.entities.request

import com.google.gson.annotations.SerializedName

data class InfoProvidedByDriver(
    @SerializedName("fuel_consumption") val fuelConsumption: Int,
    @SerializedName("fuel_price") val fuelPrice: Double,
    @SerializedName("places") val spots: List<Spots>,
    @SerializedName("shafts") val shafts: Int
)