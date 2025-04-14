package com.dadadadev.superfinancer.feature.app.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.dadadadev.social_feed.presentation.navigation.SocialFeedRoute
import com.dadadadev.superfinancer.feature.app.navigateWithBottomBar
import com.dadadadev.superfinancer.feature.finances.FinancesRoute
import com.dadadadev.superfinancer.feature.general.GeneralRoute
import kotlinx.serialization.Serializable

enum class BottomBarDestination(
    val direction: Any,
    val icon: ImageVector,
    val label: String
) {
    General(GeneralRoute, Icons.Default.Home, "Главная"),
    Finances(FinancesRoute, Icons.Default.Email, "Финансы"),
    SocialFeed(SocialFeedRoute(), Icons.Default.Search, "Лента"),
}


@Composable
fun AppBottomBar(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentHierarchy = navBackStackEntry?.destination?.hierarchy
    val currentRoute = navBackStackEntry?.destination?.route ?: ""

    BottomAppBar(modifier = modifier) {
        BottomBarDestination.entries.forEach { destination ->

            val isSelected by remember(currentRoute) {
                derivedStateOf {
                    currentHierarchy?.any { it.hasRoute(destination.direction::class) } ?: false
                }
            }

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navigateWithBottomBar(
                        destination = destination.direction,
                        navController = navController
                    )
                },
                icon = { Icon(destination.icon, contentDescription = null) },
                label = { Text(destination.label) },
            )
        }
    }
}

