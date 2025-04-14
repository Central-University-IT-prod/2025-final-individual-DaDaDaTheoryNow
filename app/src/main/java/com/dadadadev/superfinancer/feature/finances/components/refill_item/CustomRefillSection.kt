package com.dadadadev.superfinancer.feature.finances.components.refill_item

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dadadadev.superfinancer.domain.models.RefillType

@Composable
fun CustomRefillSection(
    refillTargetText: String,
    refillValue: String,
    onRefillValueChange: (String) -> Unit,
    selectedRefillType: RefillType,
    onRefillTypeSelected: (RefillType) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Настройте пополнение",
                textAlign = TextAlign.Center,
                fontSize = 17.sp,
                fontWeight = FontWeight.SemiBold
            )

            HorizontalDivider(thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))

            AnimatedVisibility(refillTargetText.isNotBlank()) {
                OutlinedTextField(
                    enabled = false,
                    value = refillTargetText,
                    onValueChange = {},
                    label = { Text("Цель") },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

            LargeDropdownMenu(
                label = "Тип Пополнения",
                items = listOf("Добавление", "Отнимание"),
                selectedIndex = if (selectedRefillType == RefillType.Increment) 0 else 1,
                onItemSelected = { index, _ ->
                    onRefillTypeSelected(if (index == 0) RefillType.Increment else RefillType.Decrement)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )

            OutlinedTextField(
                value = refillValue,
                onValueChange = {
                    onRefillValueChange(it.filter { chr -> chr.isDigit() })
                },
                label = { Text("Сумма") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}