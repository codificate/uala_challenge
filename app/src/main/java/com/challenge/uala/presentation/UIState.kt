package com.challenge.uala.presentation

import com.challenge.uala.domain.models.Place

sealed class UIState {
    data object Loading: UIState()
    data class Success(val data: List<Place>): UIState()
}