package com.dadadadev.superfinancer.feature.finances.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dadadadev.superfinancer.feature.finances.view_model.FinancesState

@Composable
fun TargetsSection(
    state: FinancesState
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.spacedBy(
                30.dp,
                Alignment.CenterHorizontally
            )
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(
                    10.dp,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val percentage = if (state.targetAmount != 0L) {
                    state.currentAmount.toFloat() / state.targetAmount
                } else {
                    1f
                }


                CircleMoneyArc(
                    currentText = "${state.currentAmount}$",
                    targetText = "${state.targetAmount}\$",
                    percentage = percentage
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Текущие накопления",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(
                    10.dp,
                    Alignment.CenterVertically
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircleMoneyArc(
                    currentText = "${(state.percentageOfFinishedGoals * 100).toInt()}%",
                    targetText = null,
                    percentage = state.percentageOfFinishedGoals
                )

                Spacer(Modifier.height(4.dp))

                Text(
                    text = "Процент выполненых целей",
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}