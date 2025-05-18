package com.zaed.ordertracker.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zaed.ordertracker.R
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
                        // todo: shipments screen content
                    }

                    HomeTabs.MASTER_PACKAGES.ordinal -> {
                        // todo: master packages screen content
                    }
                }
            }
        }
    }
}

enum class HomeTabs(
    @StringRes val titleRes: Int,
) {
    SHIPMENTS(
        titleRes = R.string.shipments,
    ),
    MASTER_PACKAGES(
        titleRes = R.string.master_packages,
    ),
}
