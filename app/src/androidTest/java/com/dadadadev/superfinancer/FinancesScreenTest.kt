package com.dadadadev.superfinancer

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dadadadev.superfinancer.feature.finances.FinancesScreen
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class FinancesScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testAddingAndDeletingGoal() {
        composeTestRule.setContent {
            FinancesScreen()
        }

        composeTestRule.onNodeWithTag("NewGoalButton").performClick()
        composeTestRule.onNodeWithTag("GoalTitleTextField").performTextInput("Машина")
        composeTestRule.onNodeWithTag("GoalCurrentAmountTextField").performTextInput("15")
        composeTestRule.onNodeWithTag("GoalTargetAmountTextField").performTextInput("30")
        composeTestRule.onNodeWithTag("AddGoalButton").performClick()
        composeTestRule.waitUntilExactlyOneExists(hasTestTag("GoalItem"))

        composeTestRule.onNode(
    hasTestTag("GoalItem") and
            hasText("15$")
        ).assertExists()

        composeTestRule.onNode(
            hasProgressBarRangeInfo(
                ProgressBarRangeInfo(
                    current = 0.5f,
                    range = 0f..1f
                )
            )
        ).assertExists()

        composeTestRule.onNodeWithTag("GoalDeleteButton").performClick()
        composeTestRule.waitUntilDoesNotExist(hasTestTag("GoalItem"))
    }
}