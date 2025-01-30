package com.challenge.uala.domain.repository

import androidx.annotation.RawRes
import com.challenge.uala.data.models.PlaceListEntity
import kotlinx.coroutines.flow.Flow

interface PlacesRepository {
    suspend fun getPlaces(@RawRes resourceId: Int): Flow<PlaceListEntity>
}