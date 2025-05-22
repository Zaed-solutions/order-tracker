package com.zaed.ordertracker.ui.masterpackagedetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.Shipment
import com.zaed.ordertracker.ui.home.FlightDetailsUiAction
import com.zaed.ordertracker.ui.home.components.ShipmentsScreenContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun MasterPackageDetailsScreen(
    modifier: Modifier = Modifier,
    masterPackageId: String,
    viewModel: MasterPackageDetailsViewModel = koinViewModel(),
    onNavigateBack: () -> Unit
) {
    LaunchedEffect(Unit) {
        viewModel.init(masterPackageId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    MasterPackageDetailsScreenContent(
        modifier = modifier,
        shipments = uiState.shipments,
        onAction = { action ->
            when (action) {
                is MasterPackageDetailsUiAction.OnBack -> onNavigateBack()
                else -> viewModel.onAction(action)
            }
        },
        windowWidthSizeClass = windowWidthSizeClass
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterPackageDetailsScreenContent(
    modifier: Modifier,
    shipments: List<Shipment>,
    onAction: (MasterPackageDetailsUiAction) -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.flight_details),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(MasterPackageDetailsUiAction.OnBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
            )
        },
        floatingActionButton = {},
    ) { innerPadding ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            ShipmentsScreenContent(
                modifier = modifier.fillMaxSize(),
                shipments = shipments,
                onAddShipment = { onAction(MasterPackageDetailsUiAction.OnAddNewShipment(it)) },
                onEditShipment = { onAction(MasterPackageDetailsUiAction.OnEditShipment(it)) },
                onDeleteShipment = { onAction(MasterPackageDetailsUiAction.OnDeleteShipment(it)) },
                windowWidthSizeClass = windowWidthSizeClass
            )
        }
    }

}
