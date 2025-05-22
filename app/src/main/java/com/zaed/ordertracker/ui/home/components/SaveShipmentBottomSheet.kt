package com.zaed.ordertracker.ui.home.components

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
import androidx.compose.material3.MaterialTheme.typography
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.ui.components.NumberInputTextField
import com.zaed.ordertracker.ui.components.TextInputTextField
import com.zaed.ordertracker.ui.components.TitledDropDownTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SaveShipmentBottomSheet(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    initialShipment: Shipment,
    onSaveShipment: (Shipment) -> Unit,
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    AnimatedVisibility(modifier = modifier, visible = isVisible) {
        var shipment by remember { mutableStateOf(initialShipment) }
        ModalBottomSheet(
            sheetState = bottomSheetState,
            onDismissRequest = onDismiss,
        ) {
            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text =
                        if (initialShipment.id.isBlank()) {
                            stringResource(R.string.add_shipment)
                        } else {
                            stringResource(
                                R.string.update_shipment,
                            )
                        },
                    style = typography.titleLarge,
                )
                TextInputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = shipment.shipmentNumber,
                    onValueChange = {
                        shipment = shipment.copy(shipmentNumber = it)
                    },
                    label = stringResource(R.string.shipment_number),
                    withBorder = true,
                )
                NumberInputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = shipment.quantity,
                    onValueChange = {
                        shipment = shipment.copy(quantity = it)
                    },
                    label = stringResource(R.string.quantity),
                    withBorder = true,
                    keyboardType = KeyboardType.Number,
                )
                NumberInputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = shipment.weight,
                    onValueChange = {
                        shipment = shipment.copy(weight = it)
                    },
                    label = stringResource(R.string.weight),
                    withBorder = true,
                )
                TitledDropDownTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = stringResource(R.string.type),
                    selectedValue = shipment.type.name,
                    options = Shipment.Type.entries.map { it.name },
                    onValueChanged = {
                        shipment = shipment.copy(type = Shipment.Type.entries[it])
                    },
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
                        onClick = { onSaveShipment(shipment) },
                    ) {
                        Text(text = stringResource(if (shipment.id.isNotBlank()) R.string.update else R.string.add))
                    }
                }
            }
        }
    }
}
