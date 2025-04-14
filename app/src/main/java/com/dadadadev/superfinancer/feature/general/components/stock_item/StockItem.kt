package com.dadadadev.superfinancer.feature.general.components.stock_item

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.dadadadev.superfinancer.domain.models.Stock
import com.dadadadev.designsystem.components.network_image.NetworkImage
import java.text.DecimalFormat

@Composable
fun StockItem(
    stock: Stock,
    onStockClick: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val colorOfStockInfo = remember {
        if (stock.percentChange == null) Color.White
        else if (stock.percentChange >= 0) Color.Green.copy(0.6f)
        else Color.Red
    }

    val stockGraphIcon = remember {
        if (stock.percentChange == null) Icons.Default.Refresh
        else if (stock.percentChange >= 0) Icons.Default.KeyboardArrowUp
        else Icons.Default.KeyboardArrowDown
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .width(200.dp)
            .height(55.dp)
            .clickable {
                onStockClick(stock.webUrl)
            }
            .testTag("StockItem")
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NetworkImage(
                imageUrl = stock.logoUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .fillMaxHeight()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (stock.currentPrice != 0.0) {
                    Text(
                        text = "${DecimalFormat("#.##").format(stock.currentPrice)}$",
                        color = colorOfStockInfo
                    )
                } else {
                    Text(
                        text = "0$",
                        color = colorOfStockInfo
                    )
                }

                if (stock.percentChange != null) {
                    Row {
                        Text(
                            text = "${DecimalFormat("#.##").format(stock.percentChange)}%",
                            color = colorOfStockInfo
                        )
                        Icon(
                            stockGraphIcon,
                            contentDescription = null,
                            tint = colorOfStockInfo
                        )
                    }
                }
            }
        }
    }
}