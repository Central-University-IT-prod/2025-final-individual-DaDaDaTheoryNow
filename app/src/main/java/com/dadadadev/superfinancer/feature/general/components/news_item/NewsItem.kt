package com.dadadadev.superfinancer.feature.general.components.news_item

import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.dadadadev.NewsArticle

@Composable
fun NewsItem(
    item: NewsArticle,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shareInSocialFeedClicked: (article: NewsArticle) -> Unit
) {
    val context = LocalContext.current
    var isShowShareSection by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .widthIn(max = 450.dp)
            .fillMaxWidth(0.9f)
            .testTag("NewsItem")
    ) {
        Box(
            modifier = modifier
                .clip(
                    MaterialTheme.shapes.medium.copy(
                        bottomEnd = CornerSize(0.dp),
                        bottomStart = CornerSize(0.dp)
                ))
                .height(200.dp)
                .background(
                    MaterialTheme.colorScheme.onBackground
                )
                .clickable(onClick = onClick)
        ) {
            ImageWithOverlay(imageUrl = item.image)

            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Row {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp)
                            .padding(start = 16.dp)
                    ) {
                        item.title?.let { title ->
                            Text(
                                text = title,
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )

                            Spacer(Modifier.height(5.dp))
                        }

                        NewsItemInfo(
                            source = item.source,
                            publishedDate = item.publishedDate ?: "Unknown",
                        )
                    }
                    IconButton(
                        onClick = {
                            isShowShareSection = !isShowShareSection
                        },
                        modifier = Modifier
                            .weight(0.2f)
                    ) {
                        Icon(
                            Icons.Default.Share,
                            contentDescription = null
                        )
                    }
                }

                Spacer(Modifier.weight(1f))
                NewsItemFooter(abstractText = item.abstract)
            }
        }

        AnimatedVisibility(isShowShareSection) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .clickable {
                            val sendIntent: Intent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, item.url)
                                type = "text/plain"
                            }
                            val shareIntent = Intent.createChooser(sendIntent, null)
                            context.startActivity(shareIntent)
                        }
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Поделиться с другом",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(
                            MaterialTheme.shapes.medium.copy(
                                topEnd = CornerSize(0.dp),
                                topStart = CornerSize(0.dp)
                            ))
                        .clickable {
                            shareInSocialFeedClicked(item)
                        }
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Поделиться в ленте",
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier
                            .padding(16.dp)
                    )
                }
            }
        }
    }
}