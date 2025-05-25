package com.zaed.ordertracker.ui.flightdetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.ui.components.MoreDropDownMenu
import com.zaed.ordertracker.ui.components.MoreDropdownItem
import com.zaed.ordertracker.ui.theme.ProjectTemplateTheme
import com.zaed.ordertracker.ui.util.formatDateTime

@Composable
fun CompactShipmentItem(
    modifier: Modifier = Modifier,
    shipment: Shipment,
    onEditShipment: () -> Unit,
    onDeleteShipment: () -> Unit,
) {
    val (backgroundColor, textColor) =
        when (shipment.type) {
            Shipment.Type.T -> listOf(Color.Blue, Color.White)
            Shipment.Type.B -> listOf(Color.Yellow, Color.Black)
        }
    Surface(
        modifier = modifier.fillMaxWidth(),
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Box(
                    modifier =
                        Modifier
                            .size(48.dp)
                            .background(
                                color = backgroundColor,
                                shape = CircleShape,
                            ),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = shipment.type.name,
                        style = MaterialTheme.typography.headlineMedium,
                        color = textColor,
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        Icon(
                            imageVector = if (shipment.exported) Icons.Default.Circle else Icons.Outlined.Circle,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(16.dp),
                        )
                        Text(
                            text = shipment.shipmentNumber,
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                    Text(
                        text = shipment.addedAt.formatDateTime(),
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = stringResource(R.string.pcs_template, shipment.quantity),
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Text(
                        text = stringResource(R.string.kg_template, shipment.weight),
                        style = MaterialTheme.typography.titleSmall,
                    )
                }
                MoreDropDownMenu(
                    isVertical = true,
                    enabled = shipment.exported.not(),
                    items =
                        listOf(
                            MoreDropdownItem(
                                title = stringResource(R.string.edit),
                                icon = Icons.Default.Edit,
                                onClick = onEditShipment,
                                tint = MaterialTheme.colorScheme.primary,
                            ),
                            MoreDropdownItem(
                                title = stringResource(R.string.delete),
                                icon = Icons.Default.DeleteOutline,
                                onClick = onDeleteShipment,
                                tint = MaterialTheme.colorScheme.error,
                            ),
                        ),
                )
            }
            HorizontalDivider(modifier = Modifier.padding(top = 16.dp), thickness = 0.5.dp)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, device = "id:pixel_9_pro")
@Composable
private fun Preview() {
    ProjectTemplateTheme {
        CompactShipmentItem(
            modifier = Modifier.padding(vertical = 48.dp),
            shipment =
                Shipment(
                    id = "",
                    shipmentNumber = "23456789123",
                    quantity = 2,
                    weight = 35.6,
                    type = Shipment.Type.T,
                    addedById = "",
                    exported = false,
                    flightId = "",
                ),
            onEditShipment = {},
        ) { }
    }
}
