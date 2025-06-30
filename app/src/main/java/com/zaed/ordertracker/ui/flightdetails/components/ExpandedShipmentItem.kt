package com.zaed.ordertracker.ui.flightdetails.components

import android.R.attr.onClick
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.ui.components.NumberInputTextField
import com.zaed.ordertracker.ui.components.TextInputTextField
import com.zaed.ordertracker.ui.util.formatDate
import com.zaed.ordertracker.ui.util.formatTime

@Composable
fun ExpandedShipmentItem(
    shipment: Shipment,
    isEditMode: Boolean,
    onEditShipment: (Shipment) -> Unit,
    onDeleteShipment: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(top = 8.dp),
    ) {
        Icon(
            imageVector = if (shipment.exported) Icons.Filled.Circle else Icons.Outlined.Circle,
            contentDescription = "Shipment Status",
            modifier =
                Modifier
                    .weight(0.5f)
                    .padding(horizontal = 8.dp),
        )
        AnimatedContent(modifier = Modifier.weight(1f), targetState = isEditMode && shipment.exported.not()) { targetState ->
            if (targetState) {
                TextInputTextField(
                    modifier = Modifier.padding(horizontal = 4.dp),
                    value = shipment.shipmentNumber,
                    withBorder = true,
                    onValueChange = { newShipmentNumber ->
                        onEditShipment(shipment.copy(shipmentNumber = newShipmentNumber))
                    },
                    keyboardType = KeyboardType.Number,
                )
            } else {
                Text(
                    text = shipment.shipmentNumber,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 4.dp),
                )
            }
        }
        AnimatedContent(modifier = Modifier.weight(1f), targetState = isEditMode && shipment.exported.not()) { targetState ->
            if (targetState) {
                NumberInputTextField(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    value = shipment.quantity,
                    withBorder = true,
                    onValueChange = { newQuantity ->
                        onEditShipment(shipment.copy(quantity = newQuantity))
                    },
                )
            } else {
                Text(
                    text = shipment.quantity.toString(),
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .padding(horizontal = 8.dp),
                )
            }
        }
        AnimatedContent(modifier = Modifier.weight(1f), targetState = isEditMode && shipment.exported.not()) { targetState ->
            if (targetState) {
                NumberInputTextField(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    value = shipment.weight,
                    withBorder = true,
                    onValueChange = { newWeight ->
                        onEditShipment(shipment.copy(weight = newWeight))
                    },
                )
            } else {
                Text(
                    text = shipment.weight.toString(),
                    textAlign = TextAlign.Center,
                    modifier =
                        Modifier
                            .padding(horizontal = 8.dp),
                )
            }
        }
        Row(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
                    .clickable{
                        if (isEditMode && shipment.exported.not()) {
                            onEditShipment(
                                shipment.copy(
                                    type =
                                        when (shipment.type) {
                                            Shipment.Type.T -> Shipment.Type.B
                                            else -> Shipment.Type.T
                                        },
                                ),
                            )
                        }
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
            if (isEditMode && shipment.exported.not()) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                )
            }
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
        if (isEditMode && shipment.exported.not()) {
            IconButton(
                modifier = Modifier.weight(1f)
                .padding(horizontal = 8.dp),
                onClick = {
                    onDeleteShipment()
                },
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    tint = Color.Red,
                )
            }
        } else {
            Spacer(
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
            )
        }
    }
}
