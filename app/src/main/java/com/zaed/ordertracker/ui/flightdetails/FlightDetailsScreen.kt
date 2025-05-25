package com.zaed.ordertracker.ui.flightdetails

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MasterPackageType
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.ui.components.MasterPackageScreenContent
import com.zaed.ordertracker.ui.components.MoreDropDownMenu
import com.zaed.ordertracker.ui.components.MoreDropdownItem
import com.zaed.ordertracker.ui.flightdetails.components.ConfirmNavigateToLoginDialog
import com.zaed.ordertracker.ui.flightdetails.components.ShipmentsScreenContent
import com.zaed.ordertracker.ui.util.exportShipmentsAsExcel
import com.zaed.ordertracker.ui.util.toRows
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun FlightDetailsScreen(
    modifier: Modifier = Modifier,
    flightId: String,
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    viewModel: FlightDetailsViewModel = koinViewModel(),
    onNavigateToMasterPackageDetails: (String) -> Unit = {},
    onNavigateToMasterPackageGroupDetails: (String, String) -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    LaunchedEffect(true) {
        viewModel.init(flightId)
    }
    FlightDetailsScreenContent(
        modifier = modifier,
        state = state,
        onAction = { action ->
            when (action) {
                FlightDetailsUiAction.NavigateBack -> onNavigateBack()
                FlightDetailsUiAction.NavigateToSettings -> onNavigateToSettings()
                is FlightDetailsUiAction.OnMasterPackageClicked -> {
                    onNavigateToMasterPackageDetails(action.masterPackage.id)
                }

                is FlightDetailsUiAction.OnMasterPackageGroupClicked -> {
                    onNavigateToMasterPackageGroupDetails(action.group.id, state.flight.id)
                }

                FlightDetailsUiAction.ExportShipments, FlightDetailsUiAction.ReExportAllShipments -> {
                    scope.launch(Dispatchers.IO) {
                        state.allShipments
                            .toRows()
                            .exportShipmentsAsExcel(
                                context = context,
                                flightNumber = state.flight.name,
                                headers =
                                    listOf(
                                        context.getString(R.string.count),
                                        context.getString(R.string.name),
                                        context.getString(R.string.date),
                                        "\t",
                                        context.getString(R.string.time),
                                        context.getString(R.string.t_b),
                                        context.getString(R.string.smno),
                                        context.getString(R.string.pcs),
                                        context.getString(R.string.kg),
                                        context.getString(R.string.mp_kg),
                                        context.getString(R.string.mp),
                                    ),
                            )?.let {
                                viewModel.handleAction(
                                    FlightDetailsUiAction.UploadExportedShipments(
                                        it,
                                    ),
                                )
                            } ?: Log.e(
                            "FlightDetailsScreen",
                            "Failed to export shipments file is null",
                        )
                    }
                }

                else -> viewModel.handleAction(action)
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlightDetailsScreenContent(
    modifier: Modifier = Modifier,
    state: FlightDetailsUiState,
    onAction: (FlightDetailsUiAction) -> Unit,
) {
    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()
    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    var isNeedToLoginSheetVisible by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(state.snackbarMessage) {
        state.snackbarMessage?.let {
            snackbarHostState.showSnackbar(
                message = it,
            )
            onAction(FlightDetailsUiAction.ResetSnackbarMessage)
        }
    }
    LaunchedEffect(key1 = state.needToLogin) {
        if (state.needToLogin) {
            isNeedToLoginSheetVisible = true
            onAction(FlightDetailsUiAction.ResetNeedToLogin)
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = state.flight.name,
                        style = MaterialTheme.typography.titleLarge,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(FlightDetailsUiAction.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    MoreDropDownMenu(
                        modifier = Modifier.padding(end = 8.dp),
                        items =
                            buildList {
                                add(
                                    MoreDropdownItem(
                                        title = stringResource(R.string.export_shipments),
                                        onClick = {
                                            onAction(FlightDetailsUiAction.ExportShipments)
                                        },
                                        icon = Icons.Default.Upload,
                                        tint = MaterialTheme.colorScheme.primary,
                                    ),
                                )

                                if (state.currentUser.admin) {
                                    add(
                                        MoreDropdownItem(
                                            title = stringResource(R.string.re_export_all_shipments),
                                            onClick = {
                                                onAction(FlightDetailsUiAction.ReExportAllShipments)
                                            },
                                            icon = Icons.Default.Upload, // Consider if this icon should be different
                                            tint = MaterialTheme.colorScheme.primary,
                                        ),
                                    )
                                }
                            },
                    )
                },
            )
        },
    ) { innerPadding ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
        ) {
            PrimaryTabRow(
                modifier = Modifier.padding(top = 16.dp),
                selectedTabIndex = pagerState.currentPage,
                indicator = {
                    TabRowDefaults.PrimaryIndicator(
                        modifier =
                            Modifier
                                .run {
                                    if (LocalLayoutDirection.current == LayoutDirection.Rtl) {
                                        scale(
                                            -1f,
                                            1f,
                                        )
                                    } else {
                                        this
                                    }
                                }.tabIndicatorOffset(pagerState.currentPage, true),
                        width = Dp.Unspecified,
                    )
                },
            ) {
                HomeTabs.entries.forEach { tab ->
                    Tab(
                        selected = pagerState.currentPage == tab.ordinal,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(tab.ordinal)
                            }
                        },
                        text = {
                            Text(
                                text = stringResource(tab.titleRes),
                            )
                        },
                    )
                }
            }
            HorizontalPager(
                modifier = Modifier.padding(top = 16.dp),
                state = pagerState,
                userScrollEnabled = false,
            ) { value ->
                when (value) {
                    HomeTabs.SHIPMENTS.ordinal -> {
                        ShipmentsScreenContent(
                            shipments = state.displayShipments,
                            onAddShipment = { shipment ->
                                onAction(FlightDetailsUiAction.AddShipment(shipment))
                            },
                            onEditShipment = { shipment ->
                                onAction(FlightDetailsUiAction.UpdateShipment(shipment))
                            },
                            onDeleteShipment = { shipmentId ->
                                onAction(FlightDetailsUiAction.DeleteShipment(shipmentId))
                            },
                            windowWidthSizeClass = windowWidthSizeClass,
                        )
                    }

                    HomeTabs.MASTER_PACKAGES.ordinal -> {
                        MasterPackageScreenContent(
                            modifier = Modifier.fillMaxSize(),
                            masterPackages = state.masterPackages,
                            masterPackageGroup = state.groups,
                            onDeleteMasterPackage = {
                                onAction(FlightDetailsUiAction.DeleteMasterPackage(it.id))
                            },
                            onEditMasterPackage = {
                                onAction(FlightDetailsUiAction.EditMasterPackage(it))
                            },
                            onAddNewMasterPackage = {
                                onAction(FlightDetailsUiAction.AddNewMasterPackage(it))
                            },
                            onMasterPackageClicked = {
                                onAction(FlightDetailsUiAction.OnMasterPackageClicked(it))
                            },
                            onMasterPackageGroupClicked = {
                                onAction(FlightDetailsUiAction.OnMasterPackageGroupClicked(it))
                            },
                            windowWidthSizeClass = windowWidthSizeClass,
                        )
                    }
                }
            }
            AnimatedVisibility(isNeedToLoginSheetVisible) {
                ModalBottomSheet(
                    onDismissRequest = {
                        isNeedToLoginSheetVisible = false
                    },
                    sheetState = rememberModalBottomSheetState(),
                ) {
                    ConfirmNavigateToLoginDialog(
                        onDismiss = {
                            isNeedToLoginSheetVisible = false
                        },
                        onConfirm = {
                            isNeedToLoginSheetVisible = false
                            onAction(FlightDetailsUiAction.NavigateToSettings)
                        },
                    )
                }
            }
        }
    }
}

enum class HomeTabs(
    @StringRes val titleRes: Int,
) {
    MASTER_PACKAGES(
        titleRes = R.string.master_packages,
    ),
    SHIPMENTS(
        titleRes = R.string.shipments,
    ),
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Composable
private fun HomePreview() {
    FlightDetailsScreenContent(
        state =
            FlightDetailsUiState(
                groups =
                    listOf(
                        MpGroup(
                            id = "1",
                            name = "Group",
                            color = 0xFF2196F3.toInt(),
                            masterPackages =
                                listOf(
                                    MasterPackage(
                                        id = "1",
                                        name = "MP1",
                                        count = 10,
                                        type = MasterPackageType.T,
                                    ),
                                    MasterPackage(
                                        id = "2",
                                        name = "MP2",
                                        count = 20,
                                        type = MasterPackageType.T,
                                    ),
                                ),
                        ),
                    ),
            ),
    ) { }
}
