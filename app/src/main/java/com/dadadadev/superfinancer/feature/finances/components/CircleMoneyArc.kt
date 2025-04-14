package com.dadadadev.superfinancer.feature.finances.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlin.math.min

@Composable
fun CircleMoneyArc(
    currentText: String,
    targetText: String?,
    percentage: Float,
    titleTextCoff: Float = 0.25f,
    bodyTextCoff: Float = 0.17f,
    dividerText: String = "из",
    spaceBetweenElements: Boolean = true,
    modifier: Modifier = Modifier,
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current

    val radius = remember(configuration) {
        with(configuration) {
            min(screenWidthDp, screenHeightDp) * 0.2f
        }.dp
    }

    val titleTextSize = with(density) {
        (radius * titleTextCoff).toSp()
    }

    val bodyTextSize = with(density) {
        (radius * bodyTextCoff).toSp()
    }

    val strokeWidth = with(density) {
        (radius * 0.08f).toPx()
    }

    var animationPlayed by rememberSaveable { mutableStateOf(false) }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage.coerceAtLeast(0f) else 0f,
        animationSpec = tween(durationMillis = 1000)
    )

    LaunchedEffect(currentPercentage) {
        animationPlayed = true
    }

    Box(
        modifier = modifier
            .size(radius * 2f)
            .aspectRatio(1f)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawArc(
                color = Color.Gray.copy(0.5f),
                -90f,
                360f,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )
            drawArc(
                color = primaryColor,
                -90f,
                360 * currentPercentage.value,
                useCenter = false,
                style = Stroke(strokeWidth, cap = StrokeCap.Round)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize(0.78f),
            verticalArrangement = Arrangement.spacedBy(
                if (spaceBetweenElements) 4.dp else 0.dp,
                Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currentText,
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                fontSize = titleTextSize,
                lineHeight = titleTextSize * 1.2f
            )

            targetText?.let {
                Text(
                    text = dividerText,
                    color = Color.Gray,
                    fontSize = bodyTextSize
                )
                Text(
                    text = targetText,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    fontSize = bodyTextSize,
                    lineHeight = bodyTextSize * 1.2f
                )
            }
        }
    }
}