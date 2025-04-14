package com.dadadadev.social_feed.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialFeedBottomSheet(
    changeBottomSheetVisibility: (Boolean) -> Unit,
    sheetContent: @Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { targetValue ->
            when (targetValue) {
                SheetValue.Hidden -> false
                SheetValue.PartiallyExpanded -> false
                SheetValue.Expanded -> true
            }
        }
    )

    ModalBottomSheet(
        onDismissRequest = { changeBottomSheetVisibility(false) },
        sheetState = sheetState,
        dragHandle = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, end = 16.dp),
                contentAlignment = Alignment.TopEnd
            ) {
                Card(
                    modifier = Modifier
                        .clip(MaterialTheme.shapes.medium)
                        .clickable {
                            changeBottomSheetVisibility(false)
                        }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
            }
        },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        sheetContent()
    }
}