package com.zaed.ordertracker.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.zaed.ordertracker.R

@Composable
fun SearchBarWithEditIcon(
    modifier: Modifier = Modifier,
    searchQuery: String,
    onUpdateSearchQuery: (String) -> Unit,
    onEditClicked: () -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SearchBar(
            modifier = Modifier.weight(1f),
            query = searchQuery,
            onQueryChanged = {
                onUpdateSearchQuery(it)
            },
        )
        IconButton(
            onClick = {
                onEditClicked()
            },
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = null,
            )
        }
    }
}
