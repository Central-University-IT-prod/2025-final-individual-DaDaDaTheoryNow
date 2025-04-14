package com.dadadadev.superfinancer.feature.finances.components.goal_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.progressBarRangeInfo
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dadadadev.superfinancer.domain.models.Goal
import com.dadadadev.superfinancer.feature.finances.components.CircleMoneyArc

@Composable
fun GoalItem(
    goal: Goal,
    onDelete: () -> Unit,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .height(100.dp)
            .fillMaxWidth()
            .padding(16.dp)
            .testTag("GoalItem")
            .semantics(mergeDescendants = true) { isTraversalGroup = false },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CircleMoneyArc(
            spaceBetweenElements = false,
            titleTextCoff = 0.11f,
            bodyTextCoff = 0.1f,
            currentText = "${goal.currentValue + goal.valueFromRefills}$",
            targetText = "${goal.targetValue}$",
            percentage = calculatePercentage(goal),
            modifier = Modifier
                .size(70.dp)
                .semantics {
                    progressBarRangeInfo = ProgressBarRangeInfo(
                        current = calculatePercentage(goal),
                        range = 0f..1f,
                        steps = 0
                    )
                }
        )

        Spacer(Modifier.width(8.dp))

        Text(
            text = goal.text,
            fontWeight = FontWeight.SemiBold
        )

        IconButton(onClick = onEdit) {
            Icon(Icons.Default.Create, contentDescription = null)
        }

        IconButton(
            onClick = onDelete,
            modifier = Modifier
                .testTag("GoalDeleteButton")
        ) {
            Icon(Icons.Default.Delete, contentDescription = null)
        }
    }
}

@Composable
fun GoalItemNoActionsWithCheckbox(
    goal: Goal,
    isSelected: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        CircleMoneyArc(
            spaceBetweenElements = false,
            titleTextCoff = 0.11f,
            bodyTextCoff = 0.1f,
            currentText = "${goal.currentValue + goal.valueFromRefills}$",
            targetText = "${goal.targetValue}$",
            percentage = calculatePercentage(goal),
            modifier = Modifier.size(70.dp)
        )

        Text(
            text = goal.text,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )

        Checkbox(
            checked = isSelected,
            onCheckedChange = { onCheckedChange(it) }
        )
    }
}

private fun calculatePercentage(
    goal: Goal
): Float {
    return if (goal.targetValue != 0L) {
        ((goal.currentValue + goal.valueFromRefills) / goal.targetValue.toFloat())
    } else {
        1f
    }
}
