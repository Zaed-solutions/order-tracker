package com.zaed.ordertracker.ui.flights.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.domain.model.Flight

@Composable
fun FlightsList(
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    onUpdateFlights: (List<Flight>) -> Unit,
    onFlightClicked: (Flight) -> Unit,
    flights: List<Flight>,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 28.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        items(flights) { flight ->
            FlightItem(
                modifier = Modifier.animateItem(),
                flight = flight,
                isEditMode = isEditMode,
                onEditFlightName = {
                    val updatedFlight = flight.copy(name = it)
                    onUpdateFlights(
                        flights.map { if (it.id == flight.id) updatedFlight else it }
                    )
                },
                onDeleteFlight = {
                    onUpdateFlights(flights.filter { it.id != flight.id })
                },
                onFlightClicked = { onFlightClicked(flight) },
            )
        }
    }
}
