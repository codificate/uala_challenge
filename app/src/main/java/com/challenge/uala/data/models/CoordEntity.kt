package com.challenge.uala.data.models

import com.google.gson.annotations.SerializedName

data class CoordEntity(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double
)