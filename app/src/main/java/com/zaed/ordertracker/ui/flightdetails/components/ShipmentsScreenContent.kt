package com.zaed.ordertracker.ui.flightdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.ui.components.NumberInputTextField
import com.zaed.ordertracker.ui.components.TextInputTextField
import com.zaed.ordertracker.ui.util.formatDate
import com.zaed.ordertracker.ui.util.formatTime

@Composable
fun ShipmentsScreenContent(
    shipments: List<Shipment>,
    isAddEnabled: Boolean,
    isEditMode: Boolean,
    onAddShipment: (Shipment) -> Unit,
    onUpdateShipments: (List<Shipment>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
        ExpandedShipmentsHeader(
            modifier = Modifier.padding(horizontal = 16.dp),
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            items(shipments) { shipment ->
                ExpandedShipmentItem(
                    modifier = Modifier.animateItem(),
                    shipment = shipment,
                    isEditMode = isEditMode,
                    onDeleteShipment = {
                        onUpdateShipments(
                            shipments.filter { it.id != shipment.id },
                        )
                    },
                    onEditShipment = { updatedShipment ->
                        onUpdateShipments(
                            shipments.map { if (it.id == updatedShipment.id) updatedShipment else it },
                        )
                    },
                )
            }
            if (isAddEnabled) {
                item(
                    key = "add_shipment_item",
                ) {
                    AddShipmentItem(
                        modifier = Modifier.animateItem(),
                        onAddShipment = onAddShipment,
                    )
                }
            }
        }
    }
}

@Composable
fun AddShipmentItem(
    onAddShipment: (Shipment) -> Unit,
    modifier: Modifier = Modifier,
) {
    var shipment by remember {
        mutableStateOf(Shipment())
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(top = 8.dp),
    ) {
        Icon(
            imageVector = Icons.Outlined.Circle,
            contentDescription = "Shipment Status",
            modifier =
                Modifier
                    .weight(0.5f)
                    .padding(horizontal = 8.dp),
        )
        TextInputTextField(
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
            value = shipment.shipmentNumber,
            onValueChange = { newShipmentNumber ->
                shipment = shipment.copy(shipmentNumber = newShipmentNumber)
            },
            withBorder = true,
            keyboardType = KeyboardType.Number,
        )

        NumberInputTextField(
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
            value = shipment.quantity,
            withBorder = true,
            onValueChange = { newQuantity ->
                shipment = shipment.copy(quantity = newQuantity)
            },
        )
        NumberInputTextField(
            modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
            value = shipment.weight,
            withBorder = true,
            onValueChange = { newWeight ->
                shipment = shipment.copy(weight = newWeight)
            },
        )

        Row(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clickable{
                        shipment =
                            shipment.copy(
                                type =
                                    when (shipment.type) {
                                        Shipment.Type.T -> Shipment.Type.B
                                        else -> Shipment.Type.T
                                    },
                            )
                    },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                modifier =
                    Modifier
                        .wrapContentSize(),
                shape = RoundedCornerShape(8.dp),
                color =
                    when (shipment.type) {
                        Shipment.Type.T -> Color.Blue
                        else -> Color.Yellow
                    },
            ) {
                Text(
                    text = shipment.type.name,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    color =
                        when (shipment.type) {
                            Shipment.Type.T -> Color.White
                            else -> Color.Black
                        },
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
            )
        }
        Text(
            text = shipment.addedAt.formatDate(),
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
        )
        Text(
            text = shipment.addedAt.formatTime(),
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
        )
        IconButton(
            modifier = Modifier.weight(1f)
                .padding(horizontal = 8.dp),
            enabled = shipment.shipmentNumber.isNotBlank(),
            onClick = {
                onAddShipment(shipment)
            },
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = Color(0xFF5F94FA),
            )
        }
    }
}

@Composable
private fun CompactShipmentsHeader(
    modifier: Modifier = Modifier,
    onAddShipmentClicked: () -> Unit,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = stringResource(R.string.shipments),
            style = MaterialTheme.typography.titleLarge,
        )
        Button(
            onClick = onAddShipmentClicked,
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
            )
        }
    }
}

@Composable
private fun ExpandedShipmentsHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(vertical = 8.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(Modifier.weight(0.5f))

                Text(
                    text = stringResource(R.string.smno),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                )

                Text(
                    text = stringResource(R.string.pcs),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                )

                Text(
                    text = stringResource(R.string.kg),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                )

                Text(
                    text = stringResource(R.string.t_b),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                )

                Text(
                    text = stringResource(R.string.date),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                )

                Text(
                    text = stringResource(R.string.time),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .weight(1f)
                            .padding(horizontal = 8.dp),
                )

                Spacer(
                    modifier =
                        Modifier
                            .weight(1f)
                            .width(48.dp)
                            .padding(horizontal = 8.dp),
                )
            }
        }
        HorizontalDivider()
    }
}
