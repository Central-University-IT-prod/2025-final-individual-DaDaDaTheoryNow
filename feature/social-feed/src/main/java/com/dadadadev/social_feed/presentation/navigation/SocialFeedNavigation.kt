package com.dadadadev.social_feed.presentation.navigation

import androidx.annotation.Keep
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.dadadadev.NewsArticle
import com.dadadadev.serializableType
import com.dadadadev.social_feed.presentation.SocialFeedScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class SocialFeedRoute(
    val articleToShare: NewsArticle? = null
)

@Keep
fun NavGraphBuilder.socialFeedScreen() {
    composable<SocialFeedRoute>(
        typeMap = mapOf(typeOf<NewsArticle?>() to serializableType<NewsArticle>(isNullableAllowed = true))
    ) { backStackEntry ->
        val article: NewsArticle? = backStackEntry.toRoute<SocialFeedRoute>().articleToShare

        SocialFeedScreen(article)
    }
}

