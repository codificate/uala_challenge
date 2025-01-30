package com.challenge.uala.data

import com.google.gson.annotations.SerializedName

data class PlaceItemResponse(
    @SerializedName("_id") val _id: Int,
    @SerializedName("coord") val coord: CoordResponse,
    @SerializedName("country") val country: String,
    @SerializedName("name") val name: String
)