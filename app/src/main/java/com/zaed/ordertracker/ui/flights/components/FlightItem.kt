package com.zaed.ordertracker.ui.flights.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.ui.components.MoreDropDownMenu
import com.zaed.ordertracker.ui.components.MoreDropdownItem
import com.zaed.ordertracker.ui.util.format

@Composable
fun FlightItem(
    modifier: Modifier = Modifier,
    onEditFlight: () -> Unit,
    onDeleteFlight: () -> Unit,
    flight: Flight,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_plane),
                contentDescription = null,
                modifier = Modifier.size(36.dp),
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = flight.name,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = flight.date.format(),
                    style = MaterialTheme.typography.titleSmall,
                )
            }
            MoreDropDownMenu(
                isVertical = true,
                items =
                    listOf(
                        MoreDropdownItem(
                            title = stringResource(R.string.edit),
                            icon = Icons.Default.Edit,
                            onClick = onEditFlight,
                            tint = MaterialTheme.colorScheme.primary,
                        ),
                        MoreDropdownItem(
                            title = stringResource(R.string.delete),
                            icon = Icons.Default.DeleteOutline,
                            onClick = onDeleteFlight,
                            tint = MaterialTheme.colorScheme.errorContainer,
                        ),
                    ),
            )
        }
    }
}
