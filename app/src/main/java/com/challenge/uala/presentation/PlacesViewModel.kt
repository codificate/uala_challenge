package com.challenge.uala.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.challenge.uala.R
import com.challenge.uala.domain.usecase.FindByTextUseCase
import com.challenge.uala.domain.usecase.GetPlacesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val getPlaces: GetPlacesUseCase,
    private val findBy: FindByTextUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState

    fun fetchPlaces() {
        viewModelScope.launch {
            getPlaces(R.raw.cities)
                .collect { places ->
                    _uiState.update { UIState.Success(places) }
                }
        }
    }

    fun findByName(name: String) {
        viewModelScope.launch {
            delay(1000L)
            _uiState.update { UIState.Loading }
            findBy(R.raw.cities, name).collect { places ->
                delay(1000L)
                _uiState.update { UIState.Success(places) }
            }
        }
    }
}