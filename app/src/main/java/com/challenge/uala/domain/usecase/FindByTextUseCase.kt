package com.challenge.uala.domain.usecase

import androidx.annotation.RawRes
import com.challenge.uala.domain.mapper.toPlaces
import com.challenge.uala.domain.repository.PlacesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FindByTextUseCase @Inject constructor(private val repository: PlacesRepository) {
    suspend operator fun invoke(@RawRes jsonFileId: Int, criteria: String) = flow {
        repository.getPlaces(jsonFileId).collect { entity ->
            val placeList = entity.toPlaces().sortedBy { place -> place.name }
            if (criteria.isEmpty() || criteria.isBlank()) {
                emit(placeList)
            } else {
                emit(placeList.filter { place -> place.name.contains(criteria) })
            }
        }
    }
}