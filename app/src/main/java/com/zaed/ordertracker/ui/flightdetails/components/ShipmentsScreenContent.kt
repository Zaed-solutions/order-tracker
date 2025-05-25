package com.zaed.ordertracker.ui.flightdetails.components

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowWidthSizeClass
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.ui.components.ConfirmDeleteBottomSheet

@Composable
fun ShipmentsScreenContent(
    modifier: Modifier = Modifier,
    shipments: List<Shipment>,
    onAddShipment: (Shipment) -> Unit,
    onEditShipment: (Shipment) -> Unit,
    onDeleteShipment: (String) -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
) {
    var selectedShipment by remember { mutableStateOf(Shipment()) }
    var isSaveShipmentBottomSheetVisible by remember { mutableStateOf(false) }
    var isDeleteShipmentBottomSheetVisible by remember { mutableStateOf(false) }
    Column(
        modifier =
            modifier
                .fillMaxWidth(),
    ) {
//        AnimatedContent(windowWidthSizeClass) {
//            when (it) {
//                WindowWidthSizeClass.COMPACT ->
//                    CompactShipmentsHeader(
//                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
//                        onAddShipmentClicked = {
//                            selectedShipment = Shipment()
//                            isSaveShipmentBottomSheetVisible = true
//                        },
//                    )
//
//                else ->
//                    ExpandedShipmentsHeader(
//                        modifier = Modifier.padding(horizontal = 16.dp),
//                        onAddShipmentClicked = {
//                            selectedShipment = Shipment()
//                            isSaveShipmentBottomSheetVisible = true
//                        },
//                    )
//            }
//        }
        LazyColumn(
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
        ) {
            items(shipments) { shipment ->
                if (windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
                    CompactShipmentItem(
                        modifier = Modifier.animateItem(),
                        shipment = shipment,
                        onDeleteShipment = {
                            selectedShipment = shipment
                            isDeleteShipmentBottomSheetVisible = true
                        },
                        onEditShipment = {
                            selectedShipment = shipment
                            isSaveShipmentBottomSheetVisible = true
                        },
                    )
                } else {
                    ExpandedShipmentItem(
                        modifier = Modifier.animateItem(),
                        shipment = shipment,
                        onDeleteShipment = {
                            selectedShipment = shipment
                            isDeleteShipmentBottomSheetVisible = true
                        },
                        onEditShipment = {
                            selectedShipment = shipment
                            isSaveShipmentBottomSheetVisible = true
                        },
                    )
                }
            }
        }
        Button(
            onClick = {
                isSaveShipmentBottomSheetVisible = true
            },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Icon(
                imageVector = Icons.Default.Save,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Add New Shipment",
                style = MaterialTheme.typography.labelLarge
            )
        }
        SaveShipmentBottomSheet(
            isVisible = isSaveShipmentBottomSheetVisible,
            initialShipment = selectedShipment,
            onDismiss = { isSaveShipmentBottomSheetVisible = false },
            onSaveShipment = { shipment ->
                if (shipment.id.isBlank()) {
                    onAddShipment(shipment)
                } else {
                    onEditShipment(shipment)
                }
                isSaveShipmentBottomSheetVisible = false
            },
        )
        ConfirmDeleteBottomSheet(
            visible = isDeleteShipmentBottomSheetVisible,
            label = stringResource(R.string.shipment),
            onDismiss = { isDeleteShipmentBottomSheetVisible = false },
            onConfirm = {
                onDeleteShipment(selectedShipment.id)
                isDeleteShipmentBottomSheetVisible = false
            },
        )
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
private fun ExpandedShipmentsHeader(
    modifier: Modifier = Modifier,
    onAddShipmentClicked: () -> Unit,
) {
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

                Button(
                    modifier =
                        Modifier
                            .weight(1f)
                            .width(48.dp)
                            .padding(horizontal = 8.dp),
                    onClick = onAddShipmentClicked,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                    )
                }
            }
        }
        HorizontalDivider()
    }
}
