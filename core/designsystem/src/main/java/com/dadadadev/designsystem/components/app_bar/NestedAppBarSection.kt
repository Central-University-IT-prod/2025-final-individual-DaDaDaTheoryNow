package com.dadadadev.designsystem.components.app_bar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedAppBarSection(
    title: String,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {
        BasicAppBar(
            title = title,
            scrollBehavior = scrollBehavior
        )

        HorizontalDivider()
    }
}