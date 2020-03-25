package com.leehendryp.androidcase.dataentry.data.request

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("point") val point: List<Double>?
)