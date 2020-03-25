package com.leehendryp.androidcase.request

import com.google.gson.annotations.SerializedName

data class InfoForAntt(
    @SerializedName("axis") val axis: Int,
    @SerializedName("distance") val distance: Double,
    @SerializedName("has_return_shipment") val hasReturnShipment: Boolean
)