package com.leehendryp.androidcase.response

import com.google.gson.annotations.SerializedName

data class Point(
    @SerializedName("point") val point: List<Double>,
    @SerializedName("provider") val provider: String
)