package com.dadadadev.superfinancer

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dadadadev.social_feed.presentation.SocialFeedScreen
import junit.framework.TestCase.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.test.KoinTest

@RunWith(AndroidJUnit4::class)
class SocialFeedScreenTest : KoinTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testFetchingPosts() {
        composeTestRule.setContent {
            SocialFeedScreen(
                articleToShare = null
            )
        }

        composeTestRule.waitUntilAtLeastOneExists(hasTestTag("ShimmerEffect"), 5000)
        composeTestRule.waitUntilDoesNotExist(hasTestTag("ShimmerEffect"), 5000)

        val postItemExists = composeTestRule
            .onAllNodes(hasTestTag("PostItem"))
            .fetchSemanticsNodes().isNotEmpty()

        val noPostsTextExists = composeTestRule
            .onAllNodes(hasText("Ещё нету постов"))
            .fetchSemanticsNodes().isNotEmpty()

        assertTrue("Neither posts nor 'no posts' text found",
            postItemExists || noPostsTextExists)
    }
}