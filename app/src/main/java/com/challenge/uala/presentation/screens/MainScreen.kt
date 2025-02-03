package com.challenge.uala.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.challenge.uala.domain.models.Place
import com.challenge.uala.presentation.PlacesViewModel
import com.challenge.uala.presentation.UIState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MainScreen(viewmodel: PlacesViewModel) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    val uiState = viewmodel.uiState.collectAsState()

    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    when (val state = uiState.value) {
        UIState.Loading -> {
            if (isLandscape) {
                Row(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }
        }

        is UIState.Success -> {
            if (isLandscape) {
                // Landscape Layout (Side-by-Side)
                Row(modifier = Modifier.fillMaxSize()) {
                    CityListColumn(state.data, selectedPlace,
                        onCityClick = { city -> selectedPlace = city },
                        onTextChanged = { value -> viewmodel.findByName(value) }
                    )
                    selectedPlace?.let { place ->
                        MapView(place, Modifier.weight(2f)) // Map occupies more space
                    }
                }
            } else {
                // Portrait Layout (Column with Map at Top)
                Column(modifier = Modifier.fillMaxSize()) {
                    selectedPlace?.let { place ->
                        MapView(
                            place, Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) // Map takes up significant portion
                    }
                    CityListColumn(
                        state.data,
                        selectedPlace,
                        onCityClick = { city -> selectedPlace = city },
                        onTextChanged = { value -> viewmodel.findByName(value) }
                    )
                }
            }
        }
    }
}

@Composable
fun CityListColumn(
    cities: List<Place>,
    selectedPlace: Place?,
    onCityClick: (Place) -> Unit,
    onTextChanged: (String) -> Unit
) {

    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
    var showBackButton by remember { mutableStateOf(false) } // State for showing back button
    val landScapeWeight by remember { mutableFloatStateOf(if (isLandscape) 1f else 0f) }

    Row(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(Color.White) // Ensure background for the list
                .weight(landScapeWeight)
        ) {

            // Conditional Back Button (Only in Portrait Mode when Map is Open)
            if (!isLandscape && showBackButton) {
                IconButton(onClick = { showBackButton = false }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }

            SearchBar { value -> onTextChanged(value) }
            CityList(cities, selectedPlace) { city ->
                onCityClick(city)
                if (!isLandscape) showBackButton = true // Show back button in portrait
            }
        }
    }
}


@Composable
fun SearchBar(onTextChanged: (String) -> Unit) {
    TextField(
        value = "", // You'll likely want to manage a search query state
        onValueChange = { txt -> if (txt.length % 2 == 0) onTextChanged(txt) }, // Handle search query changes
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clip(RoundedCornerShape(8.dp)),
        leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
        placeholder = { Text("filter") },
        textStyle = TextStyle(fontSize = 14.sp)
    )
}

@Composable
fun CityList(cities: List<Place>, selectedPlace: Place?, onCityClick: (Place) -> Unit) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(cities) { city ->
            CityRow(city, selectedPlace, onCityClick)
        }
    }
}

@Composable
fun CityRow(place: Place, selectedPlace: Place?, onCityClick: (Place) -> Unit) {
    val backgroundColor = if (place == selectedPlace) Color.LightGray else Color.White
    Text(
        text = place.name,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCityClick(place) }
            .background(backgroundColor)
            .padding(16.dp)
    )
}

@Composable
fun MapView(selectedPlace: Place, modifier: Modifier = Modifier) {
    // Replace with your actual map implementation using Google Maps Compose
    val singapore = LatLng(selectedPlace.lat, selectedPlace.lon)
    val cameraPositionState = rememberCameraPositionState()
    cameraPositionState.position = CameraPosition.fromLatLngZoom(singapore, 10f)

    GoogleMap(cameraPositionState = cameraPositionState, modifier = modifier) {
    }
}