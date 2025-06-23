package com.zaed.ordertracker.ui.flights.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Flight
import com.zaed.ordertracker.ui.components.MoreDropDownMenu
import com.zaed.ordertracker.ui.components.MoreDropdownItem
import com.zaed.ordertracker.ui.theme.ProjectTemplateTheme
import com.zaed.ordertracker.ui.util.formatDate
import com.zaed.ordertracker.ui.util.formatTime
import kotlinx.datetime.LocalDateTime

@Composable
fun FlightItem(
    modifier: Modifier = Modifier,
    isEditMode: Boolean = false,
    onEditFlightName: (String) -> Unit = {},
    onDeleteFlight: () -> Unit= {},
    onFlightClicked: () -> Unit = {},
    flight: Flight,
) {
    var tripNumber by remember { mutableStateOf(flight.name) }
    Box(
        contentAlignment = Alignment.TopStart,
    ) {
        Surface(
            modifier = modifier
                .padding(top = 12.5.dp)
                .size(width = 580.dp, height = 111.dp),
            color = Color(0xFFD7D6D6),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                onFlightClicked()
            },
            border = BorderStroke(width = 2.dp, color = Color(0xFF969595)),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Image(
                    painter = painterResource(R.drawable.img_plane),
                    contentDescription = null,
                    modifier =
                        Modifier
                            .padding(start = 22.dp)
                            .size(width = 80.dp, height = 58.dp),
                )
                if(isEditMode){
                    OutlinedTextField(
                        value = tripNumber,
                        onValueChange = {
                            tripNumber = it
                            onEditFlightName(it)
                        },
                        textStyle = TextStyle(
                            fontSize = 36.sp,
                            fontWeight = FontWeight.W600,
                            color = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f).padding(start = 14.dp),
                    )
                }else {
                    Text(
                        text = flight.name,
                        style = MaterialTheme.typography.displayMedium,
                        fontSize = 36.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.weight(1f).padding(start = 14.dp),
                    )
                }
                Column(
                ) {
                    Text(
                        text = "Date",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W400,
                    )
                    Text(
                        text = flight.date.formatDate(),
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W400,
                    )
                }
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                ) {
                    Text(
                        text = "Time",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W400,
                    )
                    Text(
                        text = flight.date.formatTime(),
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.W400,
                    )
                }
                if(isEditMode){
                    IconButton(
                        onClick = {
                            onDeleteFlight()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = Color.Red
                        )
                    }
                }
            }
        }
        Text(
            text = "Trip",
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier =
                Modifier
                    .padding(start = 50.dp)
                    .size(width = 135.dp, height = 25.dp)
                    .background(
                        color = Color(0xFF1877BA),
                        shape = RoundedCornerShape(50.dp),
                    ),
        )
    }
}

@Preview(
    showSystemUi = true,
    showBackground = true,
    device = "spec:width=1280dp,height=800dp,dpi=240",
)
@Composable
private fun FlightItemPreview() {
    ProjectTemplateTheme {
        FlightItem(
            isEditMode = true,
            flight =
                Flight(
                    id = "1",
                    name = "1635",
                    date = LocalDateTime(2025, 5, 20, 15, 30, 0, 0),
                ),
        )
    }
}
