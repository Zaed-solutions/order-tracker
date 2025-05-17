package com.zaed.ordertracker.ui.flights.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.ui.components.TextInputTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveFlightBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    onSaveFlight: (Flight) -> Unit,
    initialFlight: Flight,
) {
    val bottomSheetState = rememberModalBottomSheetState()
    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
    ) {
        var flight by remember {
            mutableStateOf(initialFlight)
        }
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = onDismiss,
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp, horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (flight.id.isBlank()) "Add Flight" else "Edit Flight",
                    style = MaterialTheme.typography.titleLarge,
                )
                TextInputTextField(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 16.dp),
                    value = flight.name,
                    onValueChange = {
                        flight = flight.copy(name = it)
                    },
                    label = stringResource(R.string.flight_number),
                    shape = MaterialTheme.shapes.large,
                    withBorder = true,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        modifier =
                            Modifier
                                .widthIn(min = 100.dp)
                                .padding(end = 8.dp)
                                .weight(1f),
                        onClick = { onDismiss() },
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(
                        modifier =
                            Modifier
                                .widthIn(min = 100.dp)
                                .weight(1f),
                        onClick = { onSaveFlight(flight) },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    ) {
                        Text(text = stringResource(if (initialFlight.id.isNotBlank()) R.string.update else R.string.add))
                    }
                }
            }
        }
    }
}
