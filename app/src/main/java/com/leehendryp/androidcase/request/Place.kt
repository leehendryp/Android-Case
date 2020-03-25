package com.leehendryp.androidcase.request

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("point") val point: List<Double>
)