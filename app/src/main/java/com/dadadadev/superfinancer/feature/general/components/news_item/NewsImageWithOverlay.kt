package com.dadadadev.superfinancer.feature.general.components.news_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dadadadev.designsystem.components.network_image.NetworkImage

@Composable
fun ImageWithOverlay(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        NetworkImage(
            imageUrl = imageUrl,
            contentDescription = null,
            blur = 2
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Color.Black.copy(alpha = 0.4f)
                )
        )
    }
}