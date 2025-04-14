package com.dadadadev.superfinancer.feature.finances.components.refill_item

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RefillBottomSheet(
    changeBottomSheetVisibility: (Boolean) -> Unit,
    skipPartiallyExpanded: Boolean = true,
    sheetContent:@Composable () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = skipPartiallyExpanded)

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