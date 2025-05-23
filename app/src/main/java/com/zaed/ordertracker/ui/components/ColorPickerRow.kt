package com.zaed.ordertracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * A color picker row with predefined colors
 */
@Composable
fun ColorPickerRow(
    selectedColor: Int,
    onColorSelected: (Int) -> Unit
) {
    val colors = listOf(
        0xFF000000.toInt(), // Black
        0xFFE91E63.toInt(), // Pink
        0xFFF44336.toInt(), // Red
        0xFFFF9800.toInt(), // Orange
        0xFFFFEB3B.toInt(), // Yellow
        0xFF4CAF50.toInt(), // Green
        0xFF2196F3.toInt(), // Blue
        0xFF3F51B5.toInt(), // Indigo
        0xFF9C27B0.toInt(), // Purple
        0xFF795548.toInt(), // Brown
        0xFF607D8B.toInt(), // Blue Grey
        0xFF9E9E9E.toInt()  // Grey
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        colors.forEach { color ->
            ColorCircle(
                color = Color(color),
                selected = color == selectedColor,
                onClick = { onColorSelected(color) }
            )
        }
    }
}