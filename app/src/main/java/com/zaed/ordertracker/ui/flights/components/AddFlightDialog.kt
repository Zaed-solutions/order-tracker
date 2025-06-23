package com.zaed.ordertracker.ui.flights.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.ui.components.TextInputTextField

@Composable
fun AddFlightDialog(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onAddFlight: (Flight) -> Unit,
    onDismiss: () -> Unit,
) {
    AnimatedVisibility(isVisible) {
        var flight by remember {
            mutableStateOf(Flight())
        }
        Dialog(
            onDismissRequest = onDismiss,
        ) {
            Column(
                modifier =
                    modifier
                        .background(color = Color.White, shape = RoundedCornerShape(50.dp))
                        .padding(vertical = 8.dp, horizontal = 16.dp)
                        .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Add Flight",
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
                        colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                    ) {
                        Text(text = stringResource(id = R.string.cancel))
                    }
                    Button(
                        modifier =
                            Modifier
                                .widthIn(min = 100.dp)
                                .weight(1f),
                        onClick = { onAddFlight(flight) },
                    ) {
                        Text(text = stringResource(R.string.add))
                    }
                }
            }
        }
    }
}
