package com.zaed.ordertracker.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.zaed.ordertracker.R

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    placeHolder: String = stringResource(R.string.search),
    onQueryChanged: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = query,
        onValueChange = {
            onQueryChanged(it)
        },
        placeholder = { Text(text = placeHolder) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
            )
        },
        trailingIcon =
            if (query.isNotEmpty()) {
                {
                    IconButton(
                        onClick = {
                            onQueryChanged("")
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                        )
                    }
                }
            } else {
                null
            },
        singleLine = true,
        shape = MaterialTheme.shapes.small,
    )
}
