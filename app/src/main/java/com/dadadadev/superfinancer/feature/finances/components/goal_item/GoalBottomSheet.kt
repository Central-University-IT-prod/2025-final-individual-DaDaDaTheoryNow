package com.dadadadev.superfinancer.feature.finances.components.goal_item

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalBottomSheet(
    changeBottomSheetVisibility: (Boolean) -> Unit,
    sheetContent:@Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { changeBottomSheetVisibility(false) },
        sheetState = sheetState,
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        sheetContent()
    }
}