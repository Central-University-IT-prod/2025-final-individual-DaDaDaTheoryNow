package com.dadadadev.superfinancer.feature.general.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp

@Composable
fun AnimatedSearchTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    isExpanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    collapsedFraction: Float
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val textFieldHeight by animateDpAsState(
        targetValue = when {
            isExpanded || value.isNotBlank() -> 95.dp
            collapsedFraction > 0.5f -> 70.dp
            else -> 95.dp
        },
        label = "TextFieldHeight"
    )

    val textFieldShape by animateDpAsState(
        targetValue = when {
            isExpanded || value.isNotBlank() -> 16.dp
            collapsedFraction > 0.5f -> 20.dp
            else -> 16.dp
        },
        label = "TextFieldShape"
    )

    Box(
        modifier = modifier
            .height(textFieldHeight)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = value,
            singleLine = true,
            onValueChange = onValueChange,
            label = { Text("Поиск") },
            leadingIcon = { Icon(Icons.Default.Search, null) },
            trailingIcon = {
                Row {
                    AnimatedVisibility(value.isNotBlank()) {
                        IconButton({
                            onValueChange("")
                        }) {
                            Icon(Icons.Default.Delete, null)
                        }
                    }

                    AnimatedVisibility(value.isBlank() && isExpanded) {
                        IconButton({
                            focusManager.clearFocus()
                            keyboardController?.hide()
                            onExpandedChange(false)
                        }) {
                            Icon(Icons.Default.Close, null)
                        }
                    }
                }
            },
            shape = RoundedCornerShape(textFieldShape),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .onFocusEvent { if (it.isFocused) onExpandedChange(true) }
        )
    }
}