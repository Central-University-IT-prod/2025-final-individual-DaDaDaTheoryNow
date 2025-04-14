package com.dadadadev.superfinancer.feature.general

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dadadadev.NewsArticle
import com.dadadadev.common.launchCustomTabs
import com.dadadadev.designsystem.components.ShimmerEffect
import com.dadadadev.designsystem.components.app_bar.NestedAppBarSection
import com.dadadadev.superfinancer.feature.general.components.AnimatedSearchTextField
import com.dadadadev.superfinancer.feature.general.components.news_item.NewsItem
import com.dadadadev.superfinancer.feature.general.components.stock_item.StocksLazyRow
import com.dadadadev.superfinancer.feature.general.view_model.GeneralAction
import com.dadadadev.superfinancer.feature.general.view_model.GeneralViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GeneralScreen(
    viewModel: GeneralViewModel = koinViewModel(),
    shareInSocialFeedClicked: (NewsArticle) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState()
    )

    val scrollState = scrollBehavior.state
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var searchText by rememberSaveable { mutableStateOf("") }

    val pullToRefreshState = rememberPullToRefreshState()
    val lazyScrollState = rememberLazyListState()

    LaunchedEffect(state.isNewsLoading) {
        lazyScrollState.scrollToItem(0)
    }

    Scaffold(
        modifier = modifier
            .pullToRefresh(
                state.isRefreshing,
                pullToRefreshState,
                onRefresh = {
                    viewModel.onAction(GeneralAction.TogglePullToRefresh)
                }
            )
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            NestedAppBarSection(
                title = "Главная",
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            AnimatedSearchTextField(
                value = searchText,
                onValueChange = {
                    searchText = it

                    // search news
                    viewModel.onAction(GeneralAction.SearchNews(searchText))
                },
                isExpanded = isExpanded,
                onExpandedChange = { isExpanded = it },
                collapsedFraction = scrollState.collapsedFraction,
                modifier = Modifier.padding(bottom = 1.5.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .testTag("NewsLazyColumn"),
                state = lazyScrollState,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    StocksLazyRow(
                        isLoading = state.isStocksLoading,
                        stocks = state.stocks,
                        onStockClick = { url ->
                            Log.d("123", url.toString())
                            url?.let {
                                context.launchCustomTabs(url)
                            }
                        }
                    )
                }

                if(state.isNewsLoading) {
                    items(10) {
                        ShimmerEffect(
                            modifier = modifier
                                .clip(MaterialTheme.shapes.medium)
                                .widthIn(max = 450.dp)
                                .fillMaxWidth(0.9f)
                                .height(200.dp)
                        )
                    }
                }

                if (!state.isNewsLoading) {
                    items(items = state.news) { article ->
                        NewsItem(
                            item = article,
                            onClick = {
                                context.launchCustomTabs(article.url)
                            },
                            shareInSocialFeedClicked = shareInSocialFeedClicked
                        )
                    }
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            Indicator(
                modifier = Modifier.align(Alignment.TopCenter),
                isRefreshing = state.isRefreshing,
                state = pullToRefreshState
            )
        }
    }
}