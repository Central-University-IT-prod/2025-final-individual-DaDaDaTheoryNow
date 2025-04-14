package com.dadadadev.superfinancer.feature.general.components.stock_item

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.dadadadev.designsystem.components.ShimmerEffect
import com.dadadadev.superfinancer.domain.models.Stock

@Composable
fun StocksLazyRow(
    isLoading: Boolean,
    stocks: List<Stock>,
    onStockClick: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) {
        if (isLoading) {
            items(10) {
                ShimmerEffect(
                    modifier = modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainer)
                        .width(200.dp)
                        .height(55.dp)
                )
            }
        }

        if (!isLoading) {
            items(stocks) { stock ->
                if (stock.currentPrice != 0.0 && stock.logoUrl != null) {
                    StockItem(
                        stock = stock,
                        onStockClick = onStockClick,
                        modifier = Modifier
                    )
                }
            }
        }
    }
}