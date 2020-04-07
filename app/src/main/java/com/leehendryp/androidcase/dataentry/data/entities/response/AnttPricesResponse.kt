package com.leehendryp.androidcase.dataentry.data.entities.response

import com.google.gson.annotations.SerializedName

data class AnttPrices(
    @SerializedName("frigorificada") val frigorificada: Double?,
    @SerializedName("geral") val geral: Double?,
    @SerializedName("granel") val granel: Double?,
    @SerializedName("neogranel") val neogranel: Double?,
    @SerializedName("perigosa") val perigosa: Double?
)