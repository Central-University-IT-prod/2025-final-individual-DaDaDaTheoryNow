package com.dadadadev.superfinancer.feature.app

import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dadadadev.social_feed.presentation.navigation.SocialFeedRoute
import com.dadadadev.social_feed.presentation.navigation.socialFeedScreen
import com.dadadadev.superfinancer.feature.app.components.AppBottomBar
import com.dadadadev.superfinancer.feature.finances.FinancesRoute
import com.dadadadev.superfinancer.feature.finances.FinancesScreen
import com.dadadadev.superfinancer.feature.general.GeneralRoute
import com.dadadadev.superfinancer.feature.general.GeneralScreen

@Composable
fun AppScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = modifier,
        bottomBar = {
            AppBottomBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = GeneralRoute,
            modifier = Modifier.padding(
                bottom = innerPadding.calculateBottomPadding()
            ),
        ) {
            composable<GeneralRoute> {
                GeneralScreen(
                    shareInSocialFeedClicked = { article ->
                        navigateWithBottomBar(
                            isRestoreState = false,
                            destination = SocialFeedRoute(article),
                            navController = navController
                        )
                    }
                )
            }

            composable<FinancesRoute> {
                FinancesScreen()
            }

            socialFeedScreen()
        }
    }
}