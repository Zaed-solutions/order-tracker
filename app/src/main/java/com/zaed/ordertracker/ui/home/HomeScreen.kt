package com.zaed.ordertracker.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Inbox
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zaed.ordertracker.R
import com.zaed.ordertracker.domain.model.MasterPackage
import com.zaed.ordertracker.domain.model.MpGroup
import com.zaed.ordertracker.ui.components.MasterPackageList
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var mpToEdit by remember { mutableStateOf<MasterPackage?>(null) }
    val pagerState = rememberPagerState { 2 }
    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {},
        floatingActionButton = {},
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
                            }
                            .tabIndicatorOffset(pagerState.currentPage, true),
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
                    HomeTabs.MASTER_PACKAGES.ordinal -> {
                        MasterPackageList(
                            modifier = Modifier.fillMaxSize(),
                            masterPackages = state.masterPackages,
                            masterPackageGroup = state.groups,
                            onEditMasterPackageGroup = {/**/},
                            onDeleteMasterPackageGroup = {},
                            onDeleteMasterPackage = {},
                            onEditMasterPackage = {},
                        )
                    }

                    HomeTabs.SHIPMENTS.ordinal -> {

                    }
                }
            }
            if (showAddDialog && state.selectedGroupId != null) {
                AddMasterPackageDialog(
                    groupId = state.selectedGroupId!!,
                    onAddMP = { groupId, name, count, type ->
                        onAction(
                            HomeUiAction.AddMasterPackage(
                                groupId = groupId,
                                name = name,
                                count = count,
                                type = type
                            )
                        )
                    },
                    onDismiss = { showAddDialog = false }
                )
            }

            mpToEdit?.let { mp ->
                EditMasterPackageDialog(
                    masterPackage = mp,
                    onUpdateMP = { updatedMp ->
                        onAction(
                            HomeUiAction.UpdateMasterPackage(updatedMp)
                        )
                    },
                    onDismiss = { mpToEdit = null }
                )
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
    HomeScreenContent(
        state = HomeUiState(
            groups = listOf(
                MpGroup(
                    id = "1",
                    name = "Group",
                    color = 0xFF2196F3.toInt(),
                    masterPackages = listOf(
                        MasterPackage(
                            id = "1",
                            name = "MP1",
                            count = 10,
                            type = "T",
                            isPnd = true
                        ),
                        MasterPackage(
                            id = "2",
                            name = "MP2",
                            count = 20,
                            type = "T",
                            isPnd = false
                        )
                    )
                )
            )
        ),
    ) { }
}
