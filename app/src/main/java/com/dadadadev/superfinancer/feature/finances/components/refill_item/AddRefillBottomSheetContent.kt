package com.dadadadev.superfinancer.feature.finances.components.refill_item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dadadadev.superfinancer.domain.models.Goal
import com.dadadadev.superfinancer.domain.models.RefillType
import okhttp3.internal.toLongOrDefault

@Composable
fun AddRefillBottomSheetContent(
    goals: List<Goal>,
    onDismiss: () -> Unit,
    onAddRefill: (
            targetText: String,
            type: RefillType,
            amount: Long,
            goalId: Int
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    var refillTargetText by remember { mutableStateOf("") }
    var refillValue by remember { mutableStateOf("") }
    var selectedRefillType by remember { mutableStateOf(RefillType.Increment) }
    var showSelectGoalSheet by remember { mutableStateOf(false) }
    var selectedGoalId by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = "Добавление Пополнения",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        GoalSelectionSection(
            goals = goals,
            showSelectGoalSheet = showSelectGoalSheet,
            selectedGoalId = selectedGoalId,
            onGoalSelected = { goalId, goalText ->
                selectedGoalId = goalId
                refillTargetText = goalText
            },
            onToggleGoalSelection = { showSelectGoalSheet = !showSelectGoalSheet }
        )

        Spacer(Modifier.height(16.dp))

        CustomRefillSection(
            refillTargetText = refillTargetText,
            refillValue = refillValue,
            onRefillValueChange = { refillValue = it },
            selectedRefillType = selectedRefillType,
            onRefillTypeSelected = { selectedRefillType = it }
        )

        OutlinedButton(
            onClick = {
                if (refillTargetText.isNotBlank()
                    && refillValue.isNotBlank()
                    && selectedGoalId != null
                ) {
                    onAddRefill(
                        refillTargetText,
                        selectedRefillType,
                        refillValue.toLongOrDefault(0),
                        selectedGoalId!!.toInt()
                    )
                    onDismiss()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text(text = "Добавить новое пополнение")
        }
    }
}