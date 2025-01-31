package com.challenge.uala.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.uala.R
import com.challenge.uala.domain.models.Place
import com.challenge.uala.domain.usecase.GetPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.time.debounce
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(private val useCase: GetPlacesUseCase) : ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    private val mutableCachePlaces: MutableStateFlow<List<Place>> = MutableStateFlow(emptyList())

    fun fetchPlaces() {
        viewModelScope.launch {
            useCase(R.raw.cities)
                .collect { places ->
                    mutableCachePlaces.update { places }
                    _uiState.update { UIState.Success(places) }
                }
        }
    }

    fun findByName(name: String) {
        viewModelScope.launch {
            _uiState.update { UIState.Loading }
            mutableCachePlaces
                .collect { places ->
                    delay(1000L)
                    _uiState.update { UIState.Success(
                        places.filter { place -> place.name.contains(name) }
                    ) }
                }

        }
    }
}