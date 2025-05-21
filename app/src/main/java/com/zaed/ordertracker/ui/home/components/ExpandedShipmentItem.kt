package com.zaed.ordertracker.ui.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.ui.components.MoreDropDownMenu
import com.zaed.ordertracker.ui.components.MoreDropdownItem
import com.zaed.ordertracker.ui.util.formatDate
import com.zaed.ordertracker.ui.util.formatTime

@Composable
fun ExpandedShipmentItem(
    modifier: Modifier = Modifier,
    shipment: Shipment,
    onEditShipment: () -> Unit,
    onDeleteShipment: () -> Unit,
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
        Text(
            text = shipment.shipmentNumber,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
        )
        Text(
            text = shipment.quantity.toString(),
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
        )
        Text(
            text = shipment.weight.toString(),
            textAlign = TextAlign.Center,
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
        )
        Surface(
            modifier =
                Modifier
                    .weight(1f)
                    .wrapContentSize()
                    .padding(horizontal = 8.dp),
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
        MoreDropDownMenu(
            modifier =
                Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp),
            items =
                listOf(
                    MoreDropdownItem(
                        title = stringResource(R.string.edit),
                        icon = Icons.Default.Edit,
                        onClick = { onEditShipment() },
                        tint = MaterialTheme.colorScheme.primary,
                    ),
                    MoreDropdownItem(
                        title = stringResource(R.string.delete),
                        icon = Icons.Default.DeleteOutline,
                        onClick = { onDeleteShipment() },
                        tint = MaterialTheme.colorScheme.error,
                    ),
                ),
        )
    }
}
