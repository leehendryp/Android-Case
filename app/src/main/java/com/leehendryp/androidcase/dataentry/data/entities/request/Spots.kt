package com.leehendryp.androidcase.dataentry.data.entities.request

import com.google.gson.annotations.SerializedName

data class Spots(
    @SerializedName("point") val point: List<Double>
)