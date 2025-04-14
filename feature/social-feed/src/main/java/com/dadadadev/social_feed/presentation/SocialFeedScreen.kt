package com.dadadadev.social_feed.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.lerp
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.dadadadev.NewsArticle
import com.dadadadev.common.launchCustomTabs
import com.dadadadev.designsystem.components.ShimmerEffect
import com.dadadadev.designsystem.components.app_bar.NestedAppBarSection
import com.dadadadev.designsystem.components.network_image.NetworkImage
import com.dadadadev.social_feed.presentation.components.ShareArticleBottomSheetContent
import com.dadadadev.social_feed.presentation.components.SocialFeedBottomSheet
import com.dadadadev.social_feed.presentation.components.TagChip
import com.dadadadev.social_feed.presentation.view_model.SocialFeedAction
import com.dadadadev.social_feed.presentation.view_model.SocialFeedViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SocialFeedScreen(
    articleToShare: NewsArticle?,
    viewModel: SocialFeedViewModel = koinViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showShareArticleSheet by rememberSaveable { mutableStateOf(articleToShare != null) }

    if (articleToShare != null && showShareArticleSheet) {
        SocialFeedBottomSheet(
            changeBottomSheetVisibility = {
                showShareArticleSheet = it
            }
        ) {
            ShareArticleBottomSheetContent(
                articleToShare = articleToShare,
                onPostArticle = { userDescription, localImagesUris, tags ->
                    showShareArticleSheet = false
                    viewModel.onAction(SocialFeedAction.ShareArticle(
                        articleToShare,
                        userDescription,
                        localImagesUris,
                        tags.toList()
                    ))
                }
            )
        }
    }

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        state = rememberTopAppBarState()
    )

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxSize(),
        topBar = {
            NestedAppBarSection(
                title = "Лента",
                scrollBehavior = scrollBehavior,
            )
        }
    ) { innerPadding ->
        if (state.posts.isEmpty() && !state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Ещё нету постов",
                    fontWeight = FontWeight.Bold
                )
            }
        }

        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 16.dp,
                bottom = 16.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (state.isLoading) {
                items(10) {
                    ShimmerEffect(
                        modifier = Modifier
                            .clip(MaterialTheme.shapes.medium)
                            .widthIn(max = 450.dp)
                            .fillMaxWidth(0.9f)
                            .height(200.dp)
                    )
                }
            }

            if (!state.isLoading) {
                items(state.posts) { post ->
                    Box(
                        modifier = Modifier
                            .widthIn(max = 450.dp)
                            .fillMaxWidth(0.9f)
                            .testTag("PostItem")
                    ) {
                        Card {
                            Column {
                                var showAllImages by remember { mutableStateOf(false) }

                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .heightIn(min = 150.dp, max = 200.dp)
                                ) {
                                    NetworkImage(
                                        imageUrl = post.imageUrl,
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clickable {
                                                post.webUrl?.let { url ->
                                                    context.launchCustomTabs(url)
                                                }
                                            }
                                    )

                                    if (post.localImagesUris.isNotEmpty()) {
                                        TextButton(
                                            onClick = {
                                                showAllImages = !showAllImages
                                            },
                                            modifier = Modifier
                                                .align(Alignment.BottomEnd)
                                        ) {
                                            Text(
                                                text = if (!showAllImages) {
                                                    "Посмотреть все изображения"
                                                } else {
                                                    "Скрыть изображения"
                                                },
                                                fontWeight = FontWeight.SemiBold,
                                                color = Color.White,
                                                textDecoration = TextDecoration.Underline
                                            )
                                        }
                                    }
                                }

                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 8.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    AnimatedVisibility(showAllImages) {
                                        LazyVerticalGrid(
                                            columns = GridCells.Adaptive(150.dp),
                                            modifier = Modifier.heightIn(
                                                min = 150.dp,
                                                max = 750.dp
                                            ),
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            items(post.localImagesUris) { uri ->
                                                NetworkImage(
                                                    imageUrl = uri,
                                                    modifier = Modifier
                                                        .padding(8.dp)
                                                        .aspectRatio(1f)
                                                        .clip(MaterialTheme.shapes.medium)
                                                        .heightIn(min = 150.dp, max = 250.dp)
                                                )
                                            }
                                        }
                                    }

                                    Text(
                                        text = post.title,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(Modifier.height(8.dp))

                                    TruncatedText(post.userDescription)

                                    if (post.tags.isNotEmpty()) {
                                        FlowRow(
                                            modifier = Modifier
                                                .padding(16.dp),
                                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                                            verticalArrangement = Arrangement.spacedBy(3.dp),
                                        ) {
                                            post.tags.forEach { tag ->
                                                TagChip(
                                                    text = tag,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        GradientFavoriteButton(
                            isFavorite = post.isFavorite,
                            onChangeFavorite = {
                                viewModel.onAction(
                                    SocialFeedAction.TogglePostToFavorite(
                                        post
                                    )
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TruncatedText(
    text: String,
    modifier: Modifier = Modifier
) {
    var isTruncated by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.End,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize()
        ) {
            Text(
                text = text,
                maxLines = if (expanded) Int.MAX_VALUE else 3,
                textAlign = TextAlign.Center,
                overflow = TextOverflow.Ellipsis,
                onTextLayout = { textLayoutResult ->
                    if (!expanded) {
                        isTruncated = textLayoutResult.hasVisualOverflow
                    }
                },
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }

        if (isTruncated || expanded) {
            TextButton(onClick = { expanded = !expanded }) {
                Text(if (expanded) "Свернуть" else "Показать всё")
            }
        }
    }
}

@Composable
private fun BoxScope.GradientFavoriteButton(
    isFavorite: Boolean,
    onChangeFavorite: () -> Unit,
) {
    val animationProgress by animateFloatAsState(
        targetValue = if (isFavorite) 1f else 0f,
        animationSpec = tween(durationMillis = 600),
        label = "gradientAnimation"
    )

    val startColor = lerp(Color.Gray, Color(0xE2FFC107), animationProgress)
    val endColor = lerp(Color.Gray, Color(0xFF516E86), animationProgress)

    val animatedBrush = Brush.linearGradient(
        colors = listOf(startColor, endColor)
    )

    Box(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(16.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(animatedBrush)
            .clickable {
                onChangeFavorite()
            }
    ) {
        Icon(
            imageVector = if (isFavorite) Icons.Default.Star else Icons.Outlined.Star,
            contentDescription = null,
            tint = if (isFavorite) Color.Yellow else Color(0xFF6A7075),
            modifier = Modifier.padding(12.dp)
        )
    }
}

