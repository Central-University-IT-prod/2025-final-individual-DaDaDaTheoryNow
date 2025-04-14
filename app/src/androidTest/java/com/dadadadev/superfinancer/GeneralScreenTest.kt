package com.dadadadev.superfinancer

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dadadadev.superfinancer.feature.general.GeneralScreen
import com.dadadadev.superfinancer.feature.general.view_model.GeneralViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest
import org.koin.test.inject


@RunWith(AndroidJUnit4::class)
class GeneralScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testSearchFieldInput() {
        composeTestRule.setContent {
            GeneralScreen(
                shareInSocialFeedClicked = {}
            )
        }

        composeTestRule.onNodeWithText("Поиск").performTextInput("test")
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testFetchingNewsAndPullToRefresh() {
        val viewModel: GeneralViewModel by inject()

        composeTestRule.setContent {
            GeneralScreen(
                viewModel = viewModel,
                shareInSocialFeedClicked = {}
            )
        }

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag("ShimmerEffect"), 5000)
        composeTestRule.waitUntilAtLeastOneExists(hasTestTag("NewsItem"), 5000)

        composeTestRule.onNodeWithTag("NewsLazyColumn")
            .performTouchInput { swipeDown() }

        composeTestRule.waitUntil(5000) {
            viewModel.state.value.isRefreshing
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testStocks() {
        val viewModel: GeneralViewModel by inject()

        composeTestRule.setContent {
            GeneralScreen(
                viewModel = viewModel,
                shareInSocialFeedClicked = {}
            )
        }

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag("StockItem"), 5000)
    }
}