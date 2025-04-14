package com.dadadadev.superfinancer.feature.finances.components.goal_item

import com.dadadadev.superfinancer.domain.models.Goal
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import okhttp3.internal.toLongOrDefault

@Composable
fun EditGoalBottomSheetContent(
    onDismiss: () -> Unit,
    goal: Goal,
    onChange: (text: String, currentValue: Long, targetValue: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var goalText by remember { mutableStateOf(goal.text) }
    var targetValue by remember { mutableStateOf(goal.targetValue.toString()) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Изменение цели",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = goalText,
            onValueChange = { goalText = it },
            label = { Text("Название цели") },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth(),
        )

        OutlinedTextField(
            value = targetValue,
            onValueChange = { targetValue = it.filter { c -> c.isDigit() } },
            label = { Text("Целевая сумма") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = MaterialTheme.shapes.medium,

            modifier = Modifier.fillMaxWidth()
        )

        OutlinedButton(
            onClick = {
                if (goalText.isNotBlank() && targetValue.isNotBlank()) {
                    onChange(
                        goalText,
                        goal.currentValue,
                        targetValue.toLongOrDefault(0)
                    )
                    onDismiss()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Изменить цель")
        }
    }
}