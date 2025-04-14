package com.dadadadev.designsystem.components.network_image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest
import coil3.request.transformations
import com.dadadadev.designsystem.R
import com.dadadadev.designsystem.components.ShimmerEffect

@Composable
fun NetworkImage(
    imageUrl: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    blur: Int = 0,
    placeholder: @Composable () -> Unit = { ImagePlaceholder(modifier) }
) {
    if (imageUrl.isNullOrBlank()) {
        placeholder()
    } else {
        SubcomposeAsyncImage(
            modifier = modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalPlatformContext.current)
                .data(imageUrl)
                .apply {
                    if (blur > 0) {
                        transformations(
                            BlurTransformation(radius = blur)
                        )
                    }
                }
                .build(),
            contentDescription = contentDescription
        ) {
            val state by painter.state.collectAsState()

            when (state) {
                is AsyncImagePainter.State.Success -> {
                    SubcomposeAsyncImageContent(
                        contentScale = ContentScale.Crop
                    )
                }
                is AsyncImagePainter.State.Loading -> {
                    ShimmerEffect(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
                is AsyncImagePainter.State.Error -> {
                    placeholder()
                }
                else -> Unit
            }
        }
    }
}

@Composable
private fun ImagePlaceholder(modifier: Modifier) {
    Image(
        painterResource(R.drawable.no_image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
    )
}