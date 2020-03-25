package com.leehendryp.androidcase.dataentry.data.request

import com.google.gson.annotations.SerializedName

data class InfoProvidedByDriver(
    @SerializedName("fuel_consumption") val fuelConsumption: Int?,
    @SerializedName("fuel_price") val fuelPrice: Double?,
    @SerializedName("places") val places: List<Place>?
)