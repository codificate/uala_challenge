package com.challenge.uala.domain.mapper

import com.challenge.uala.data.models.PlaceListEntity
import com.challenge.uala.domain.models.Place

fun PlaceListEntity.toPlaces(): List<Place> {
    return this.places.map { itemEntity ->
        Place(
            itemEntity._id,
            itemEntity.name.plus(", ").plus(itemEntity.country),
            itemEntity.coord.lat,
            itemEntity.coord.lon
        )
    }
}
