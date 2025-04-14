package com.dadadadev.superfinancer.feature.finances.components.goal_item

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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import okhttp3.internal.toLongOrDefault

@Composable
fun AddGoalBottomSheetContent(
    onDismiss: () -> Unit,
    onAddGoal: (String, Long, Long) -> Unit,
    modifier: Modifier = Modifier
) {
    var goalText by remember { mutableStateOf("") }
    var currentValue by remember { mutableStateOf("") }
    var targetValue by remember { mutableStateOf("") }
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
            text = "Новая цель",
            style = MaterialTheme.typography.headlineSmall
        )

        OutlinedTextField(
            value = goalText,
            onValueChange = { goalText = it },
            label = { Text("Название цели") },
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("GoalTitleTextField"),
        )

        OutlinedTextField(
            value = currentValue,
            onValueChange = { currentValue = it.filter { c -> c.isDigit() } },
            label = { Text("Текущая сумма") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = MaterialTheme.shapes.medium,

            modifier = Modifier
                .fillMaxWidth()
                .testTag("GoalCurrentAmountTextField")
        )

        OutlinedTextField(
            value = targetValue,
            onValueChange = { targetValue = it.filter { c -> c.isDigit() } },
            label = { Text("Целевая сумма") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = MaterialTheme.shapes.medium,

            modifier = Modifier
                .fillMaxWidth()
                .testTag("GoalTargetAmountTextField")
        )

        OutlinedButton(
            onClick = {
                if (goalText.isNotBlank() && currentValue.isNotBlank() && targetValue.isNotBlank()) {
                    onAddGoal(
                        goalText,
                        currentValue.toLongOrDefault(0),
                        targetValue.toLongOrDefault(0)
                    )
                    onDismiss()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .testTag("AddGoalButton")
        ) {
            Text("Добавить цель")
        }
    }
}