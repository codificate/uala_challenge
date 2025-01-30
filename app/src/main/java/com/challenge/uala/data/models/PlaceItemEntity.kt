package com.challenge.uala.data.models

import com.google.gson.annotations.SerializedName

data class PlaceItemEntity(
    @SerializedName("_id") val _id: Int,
    @SerializedName("coord") val coord: CoordEntity,
    @SerializedName("country") val country: String,
    @SerializedName("name") val name: String
)