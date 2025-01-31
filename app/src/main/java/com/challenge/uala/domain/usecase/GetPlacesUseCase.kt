package com.challenge.uala.domain.usecase

import androidx.annotation.RawRes
import com.challenge.uala.domain.mapper.toPlaces
import com.challenge.uala.domain.repository.PlacesRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPlacesUseCase @Inject constructor(private val repository: PlacesRepository) {
    suspend operator fun invoke(@RawRes jsonFileId: Int) = flow {
        repository.getPlaces(jsonFileId).collect { entity ->
            emit(entity.toPlaces().sortedBy { place -> place.name })
        }
    }
}