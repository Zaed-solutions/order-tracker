package com.zaed.ordertracker.ui.flights.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.domain.model.Flight

@Composable
fun FlightsList(
    modifier: Modifier = Modifier,
    onEditFlight: (Flight) -> Unit,
    onDeleteFlight: (Flight) -> Unit,
    flights: List<Flight>,
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.FixedSize(380.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        items(flights) { flight ->
            FlightItem(
                modifier = Modifier.animateItem(),
                flight = flight,
                onEditFlight = { onEditFlight(flight) },
                onDeleteFlight = { onDeleteFlight(flight) },
            )
        }
    }
}
