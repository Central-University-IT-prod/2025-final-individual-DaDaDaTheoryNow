package com.dadadadev.social_feed.presentation.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.dadadadev.designsystem.components.network_image.NetworkImage
import com.dadadadev.NewsArticle

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ShareArticleBottomSheetContent(
    articleToShare: NewsArticle,
    onPostArticle: (userDescription: String, localImagesUris: List<String>, tags: List<String>) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var userDescriptionText by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()

    val tags = remember { mutableStateListOf<String>() }
    var tagInput by remember { mutableStateOf("") }

    val localImagesUris = remember { mutableStateListOf<String>() }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    )  { newUri: Uri? ->
        if (newUri == null) return@rememberLauncherForActivityResult

        val input = context.contentResolver.openInputStream(newUri) ?: return@rememberLauncherForActivityResult
        val outputFile = context.filesDir.resolve("profilePic_${System.currentTimeMillis()}.jpg")
        input.copyTo(outputFile.outputStream())
        localImagesUris.add(outputFile.toUri().toString())
    }

    Column(
        modifier = modifier
            .verticalScroll(scrollState)
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Column {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(150.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 150.dp, max = 750.dp),
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp, Alignment.CenterHorizontally
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        Box(
                            modifier = Modifier
                                .padding(8.dp)
                                .aspectRatio(1f)
                                .border(1.dp, Color.Gray, MaterialTheme.shapes.medium)
                                .clip(MaterialTheme.shapes.medium)
                                .clickable {
                                    launcher.launch("image/*")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.Add,
                                contentDescription = null
                            )
                        }
                    }

                    item {
                        NetworkImage(
                            imageUrl = articleToShare.image,
                            modifier = Modifier
                                .padding(8.dp)
                                .aspectRatio(1f)
                                .clip(MaterialTheme.shapes.medium)
                                .heightIn(min = 150.dp, max = 250.dp)
                        )
                    }

                    items(localImagesUris) { imageUrl ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                        ) {
                            NetworkImage(
                                imageUrl = imageUrl,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(MaterialTheme.shapes.medium)
                                    .aspectRatio(1f)
                            )

                            IconButton(
                                onClick = {
                                    localImagesUris.removeIf {
                                        it == imageUrl
                                    }
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 8.dp, end = 8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = articleToShare.title ?: "Без заголовка",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = articleToShare.abstract,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        OutlinedTextField(
            value = userDescriptionText,
            onValueChange = {
                userDescriptionText = it
            },
            label = { Text("Опишите своё мнение") },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = tagInput,
            shape = MaterialTheme.shapes.medium,
            onValueChange = { tagInput = it },
            label = { Text("Добавьте теги (можно через запятую)") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            trailingIcon = {
                AnimatedVisibility(tagInput.isNotBlank()) {
                    IconButton(
                        onClick = {
                            tagInput.split(",")
                                .map { it.trim() }
                                .filter { it.isNotBlank() }
                                .forEach { tags.add(it) }
                            tagInput = ""
                        }
                    ) {
                        Icon(
                            Icons.Default.Done,
                            contentDescription = null
                        )
                    }
                }
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    tagInput.split(",")
                        .map { it.trim() }
                        .filter { it.isNotBlank() }
                        .forEach { tags.add(it) }
                    tagInput = ""
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
        )

        FlowRow(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            tags.forEach { tag ->
                TagChip(
                    text = tag,
                    onDelete = {
                        tags.removeIf { it == tag }
                    }
                )
            }
        }

        OutlinedButton(
            onClick = {
                if (userDescriptionText.isNotBlank()) {
                    onPostArticle(
                        userDescriptionText,
                        localImagesUris.toList(),
                        tags
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Поделиться"
            )
        }
    }
}