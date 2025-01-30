package com.challenge.uala.data.repository

import androidx.annotation.RawRes
import com.challenge.uala.data.datasource.PlacesDatasource
import com.challenge.uala.data.models.PlaceListEntity
import com.challenge.uala.domain.models.Place
import com.challenge.uala.domain.repository.PlacesRepository
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlacesRepositoryImpl @Inject constructor(
    private val datasource: PlacesDatasource,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val gson: Gson = Gson()
) : PlacesRepository {

    override suspend fun getPlaces(@RawRes resourceId: Int) = flow {
        withContext(dispatcher) {
            val state = datasource.readJsonFromRaw(resourceId)?.let { placesJson ->
                gson.fromJson(placesJson, PlaceListEntity::class.java)
            } ?: run {
                PlaceListEntity(emptyList())
            }
            emit(state)
        }
    }
}