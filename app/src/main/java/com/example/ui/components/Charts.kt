package com.example.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.ExpenseEntity
import com.example.ui.theme.PastelLavender
import com.example.ui.theme.PastelMint
import com.example.ui.theme.PastelPeach
import com.example.ui.theme.PastelBlush

// Color palette for chart slices
val ChartColors = listOf(
    Color(0xFF6B1D2F), // Burgundy
    Color(0xFF6A4C93), // Lavender
    Color(0xFF2E6F40), // Sage
    Color(0xFFC04A27), // Coral
    Color(0xFF1E6091), // Ocean
    Color(0xFFD97706), // Amber
    Color(0xFF0284C7), // Sky Blue
    Color(0xFF10B981)  // Emerald
)

/**
 * Animated Donut / Pie Chart for Expense Categories
 */
@Composable
fun CategoryDonutChart(
    expenses: List<ExpenseEntity>,
    currencySymbol: String,
    modifier: Modifier = Modifier
) {
    val categoryTotals = expenses.groupBy { it.category }
        .mapValues { entry -> entry.value.sumOf { it.amount } }
        .filterValues { it > 0 }

    val totalAmount = categoryTotals.values.sum()

    if (totalAmount == 0.0 || categoryTotals.isEmpty()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No category data recorded yet",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
        return
    }

    val progressAnim = remember { Animatable(0f) }
    LaunchedEffect(expenses) {
        progressAnim.animateTo(1f, animationSpec = tween(1000))
    }

    val categoriesList = categoryTotals.entries.toList()

    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Canvas Donut Chart
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .padding(8.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.size(120.dp)) {
                    var startAngle = -90f
                    val strokeWidth = 26.dp.toPx()

                    categoriesList.forEachIndexed { index, entry ->
                        val sweepAngle = ((entry.value / totalAmount) * 360f * progressAnim.value).toFloat()
                        val color = ChartColors[index % ChartColors.size]

                        drawArc(
                            color = color,
                            startAngle = startAngle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
                            size = Size(size.width, size.height)
                        )
                        startAngle += sweepAngle
                    }
                }

                // Center label
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Total",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 10.sp
                        )
                    )
                    Text(
                        text = "$currencySymbol${totalAmount.toInt()}",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 14.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Category Legend
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                categoriesList.take(5).forEachIndexed { index, entry ->
                    val color = ChartColors[index % ChartColors.size]
                    val percentage = (entry.value / totalAmount * 100).toInt()

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(color)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = entry.key,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 12.sp
                                )
                            )
                        }
                        Text(
                            text = "$percentage%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 * Daily Expense Spending Bar Chart
 */
@Composable
fun SpendingBarChart(
    expenses: List<ExpenseEntity>,
    currencySymbol: String,
    modifier: Modifier = Modifier
) {
    val dailyList = expenses.groupBy { it.dateDisplay.ifBlank { "Recent" } }
        .mapValues { entry -> entry.value.sumOf { it.amount } }
        .entries.toList().takeLast(6)

    if (dailyList.isEmpty()) return

    val maxAmount = (dailyList.maxOfOrNull { it.value } ?: 1.0).coerceAtLeast(10.0)

    val anim = remember { Animatable(0f) }
    LaunchedEffect(expenses) {
        anim.animateTo(1f, animationSpec = tween(800))
    }

    val barColor = MaterialTheme.colorScheme.primary

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Spending Trend History",
            style = MaterialTheme.typography.titleSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(130.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Bottom
        ) {
            dailyList.forEach { entry ->
                val barRatio = (entry.value / maxAmount * anim.value).toFloat().coerceIn(0.05f, 1f)

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Bottom,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "$currencySymbol${entry.value.toInt()}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Box(
                        modifier = Modifier
                            .width(22.dp)
                            .height((100 * barRatio).dp)
                            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
                            .background(barColor)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = entry.key,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}
