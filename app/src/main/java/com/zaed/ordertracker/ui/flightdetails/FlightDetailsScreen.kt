package com.zaed.ordertracker.ui.flightdetails

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Upload
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedIconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
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
import com.zaed.ordertracker.ui.components.SearchBar
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
    var isEditMode by remember { mutableStateOf(false) }
    var updatedShipments by remember (state.displayShipments){ mutableStateOf(state.displayShipments) }
    var updatedMasterPackages by remember (state.displayedMasterPackages){ mutableStateOf(state.displayedMasterPackages) }
    var updatedMPGroups by remember (state.displayedGroups){ mutableStateOf(state.displayedGroups) }
    var isAddEnabled by remember { mutableStateOf(false) }
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
        bottomBar = {
            Row(
                modifier = Modifier.padding(vertical = 24.dp).fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    onClick = {
                        isAddEnabled = true
                    },
                    modifier = Modifier.size(height = 70.dp, width = 290.dp),
                    enabled = !isAddEnabled,
                    colors =
                        ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFF5F94FA),
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        modifier = Modifier.size(42.dp),
                    )
                }
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = state.flight.name,
                            style = MaterialTheme.typography.titleMedium,
                        )
                        SearchBar(
                            modifier = Modifier.weight(1f),
                            query = if (pagerState.currentPage == HomeTabs.SHIPMENTS.ordinal) state.shipmentSearchQuery else state.masterPackageSearchQuery
                        ) {
                            onAction(
                                if (pagerState.currentPage == HomeTabs.SHIPMENTS.ordinal)
                                    FlightDetailsUiAction.UpdateShipmentSearchQuery(it)
                                else
                                    FlightDetailsUiAction.UpdateMasterPackageSearchQuery(it)
                            )
                        }
                    }
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
            Row(
                modifier = Modifier.padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                PrimaryTabRow(
                    modifier = Modifier.weight(1f),
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
                if (isEditMode) {
                    Button(
                        onClick = {
                            onAction(
                                if (pagerState.currentPage == HomeTabs.SHIPMENTS.ordinal) {
                                    FlightDetailsUiAction.UpdateShipmentsList(updatedShipments)
                                } else {
                                    FlightDetailsUiAction.UpdateMasterPackages(
                                        updatedMasterPackages,
                                        updatedMPGroups
                                    )
                                },
                            )
                            isEditMode = false
                        },
                    ) {
                        Text(
                            text = "Save",
                        )
                    }
                    OutlinedIconButton(
                        onClick = {
                            isEditMode = false
                            if( pagerState.currentPage == HomeTabs.SHIPMENTS.ordinal) {
                                updatedShipments = state.displayShipments
                            } else {
                                updatedMasterPackages = state.displayedMasterPackages
                                updatedMPGroups = state.displayedGroups
                            }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error,
                        )
                    }
                } else {
                    IconButton(
                        onClick = {
                            isEditMode = true
                        },
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_edit),
                            contentDescription = null,
                        )
                    }
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
                            shipments = updatedShipments,
                            isAddEnabled = isAddEnabled,
                            isEditMode = isEditMode,
                            onAddShipment = { shipment ->
                                onAction(FlightDetailsUiAction.AddShipment(shipment))
                                isAddEnabled = false
                            },
                            onUpdateShipments = { shipments ->
                                updatedShipments = shipments
                            },
                        )
                    }

                    HomeTabs.MASTER_PACKAGES.ordinal -> {
                        MasterPackageScreenContent(
                            modifier = Modifier.fillMaxSize(),
                            isAddEnabled = isAddEnabled,
                            isEditMode = isEditMode,
                            masterPackages = updatedMasterPackages,
                            masterPackageGroup = updatedMPGroups,
                            onUpdateMasterPackages = { masterPackages ->
                                updatedMasterPackages = masterPackages
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
                            searchQuery = state.masterPackageSearchQuery,
                            onUpdateSearchQuery = {
                                onAction(FlightDetailsUiAction.UpdateMasterPackageSearchQuery(it))
                            },
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
                allGroups =
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
