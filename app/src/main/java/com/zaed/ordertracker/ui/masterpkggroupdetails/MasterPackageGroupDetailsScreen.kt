package com.zaed.ordertracker.ui.masterpkggroupdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.window.core.layout.WindowWidthSizeClass
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.ui.components.EditMpGroupDialog
import com.zaed.ordertracker.ui.components.MasterPackageScreenContent
import org.koin.androidx.compose.koinViewModel

@Composable
fun MasterPackageGroupDetailsScreen(
    modifier: Modifier = Modifier,
    groupId: String,
    flightId: String,
    viewModel: MasterPackageGroupDetailsViewModel = koinViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToMasterPackageDetails: (String) -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.init(groupId, flightId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val windowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
    MasterPackageGroupDetailsScreenContent(
        modifier = modifier,
        masterPackages = uiState.masterPackages,
        group = uiState.group,
        onAction = { action ->
            when (action) {
                is MasterPackageGroupDetailsUiAction.OnBack -> onNavigateBack()
                is MasterPackageGroupDetailsUiAction.OnMasterPackageClicked -> {
                    onNavigateToMasterPackageDetails(action.masterPackage.id)
                }
                else -> viewModel.onAction(action)
            }
        },
        windowWidthSizeClass = windowWidthSizeClass
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MasterPackageGroupDetailsScreenContent(
    modifier: Modifier,
    masterPackages: List<MasterPackage>,
    group: MpGroup,
    onAction: (MasterPackageGroupDetailsUiAction) -> Unit,
    windowWidthSizeClass: WindowWidthSizeClass,
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var tempGroupName by remember { mutableStateOf(group.name) }
    var tempGroupColor by remember { mutableStateOf(group.color) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Group Details: ${group.name}",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(MasterPackageGroupDetailsUiAction.OnBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { 
                        tempGroupName = group.name
                        tempGroupColor = group.color
                        showEditDialog = true 
                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Group"
                        )
                    }
                }
            )
        },
        floatingActionButton = {},
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            MasterPackageScreenContent(
                modifier = modifier.fillMaxSize(),
                masterPackages = masterPackages,
                masterPackageGroup = emptyList(), // We don't need to display groups here
                onAddNewMasterPackage = { onAction(MasterPackageGroupDetailsUiAction.OnAddNewMasterPackage(it)) },
                onEditMasterPackage = { onAction(MasterPackageGroupDetailsUiAction.OnEditMasterPackage(it)) },
                onDeleteMasterPackage = { onAction(MasterPackageGroupDetailsUiAction.OnDeleteMasterPackage(it.id)) },
                onMasterPackageClicked = { onAction(MasterPackageGroupDetailsUiAction.OnMasterPackageClicked(it)) },
                windowWidthSizeClass = windowWidthSizeClass
            )
        }

        if (showEditDialog) {
            EditMpGroupDialog(
                tempMpGroupName = tempGroupName,
                tempMpGroupColor = tempGroupColor,
                onMpGroupNameChange = { tempGroupName = it },
                onMpGroupColorChange = { tempGroupColor = it },
                canEditName = group.canEditName,
                onConfirm = {
                    val updatedGroup = group.copy(
                        name = tempGroupName,
                        color = tempGroupColor
                    )
                    onAction(MasterPackageGroupDetailsUiAction.OnEditGroup(updatedGroup))
                    showEditDialog = false
                },
                onDismiss = { showEditDialog = false }
            )
        }
    }
}
