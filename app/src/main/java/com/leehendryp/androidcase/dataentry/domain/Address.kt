package com.leehendryp.androidcase.dataentry.domain

import com.google.gson.annotations.SerializedName

data class Address(
    @SerializedName("name") val name: String,
    @SerializedName("formatted_address") val formattedAddress: String,
    @SerializedName("location") val location: Location
)