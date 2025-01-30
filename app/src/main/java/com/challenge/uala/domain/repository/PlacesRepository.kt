package com.challenge.uala.domain.repository

import com.challenge.uala.domain.models.Place

interface PlacesRepository {
    suspend fun getPlaces(): List<Place>
    suspend fun findPlaces(byName: String): List<Place>
}