package com.zaed.ordertracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MoreDropDownMenu(
    modifier: Modifier = Modifier,
    isVertical: Boolean = true,
    items: List<MoreDropdownItem>,
) {
    var isOptionsMenuVisible by remember { mutableStateOf(false) }
    Box(
        modifier =
            modifier
                .wrapContentSize(Alignment.TopEnd),
    ) {
        IconButton(
            onClick = { isOptionsMenuVisible = !isOptionsMenuVisible },
        ) {
            Icon(
                imageVector = if (isVertical) Icons.Default.MoreVert else Icons.Default.MoreHoriz,
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = isOptionsMenuVisible,
            onDismissRequest = { isOptionsMenuVisible = false },
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        item.onClick()
                        isOptionsMenuVisible = false
                    },
                    text = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                        ) {
                            Text(
                                text = item.title,
                                color = item.tint,
                            )
                            if (item.icon != null) {
                                Icon(
                                    modifier = Modifier.padding(start = 16.dp),
                                    imageVector = item.icon,
                                    tint = item.tint,
                                    contentDescription = null,
                                )
                            }
                        }
                    },
                )
            }
        }
    }
}

data class MoreDropdownItem(
    val onClick: () -> Unit = {},
    val title: String = "",
    val icon: ImageVector? = null,
    val tint: Color,
)
