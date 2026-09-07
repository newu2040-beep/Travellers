package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppCurrency
import com.example.ui.theme.BurgundyPrimary
import com.example.ui.theme.StatusGreen

@Composable
fun FinancialOverviewCards(
    totalBudget: Double,
    totalSpent: Double,
    currency: AppCurrency,
    modifier: Modifier = Modifier
) {
    val remaining = (totalBudget - totalSpent).coerceAtLeast(0.0)
    val usedPercent = if (totalBudget > 0) ((totalSpent / totalBudget) * 100).toInt() else 0
    val remainingPercent = (100 - usedPercent).coerceAtLeast(0)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Total Budget Card
        FinanceCardItem(
            title = "Total Budget",
            amount = currency.format(totalBudget),
            progress = (usedPercent / 100f).coerceIn(0f, 1f),
            progressColor = BurgundyPrimary,
            progressText = "$usedPercent% used",
            testTag = "total_budget_card",
            modifier = Modifier.weight(1f)
        )

        // Total Spent Card
        FinanceCardItem(
            title = "Total Spent",
            amount = currency.format(totalSpent),
            progress = (usedPercent / 100f).coerceIn(0f, 1f),
            progressColor = BurgundyPrimary,
            progressText = "$usedPercent%",
            testTag = "total_spent_card",
            modifier = Modifier.weight(1f)
        )

        // Remaining Card
        FinanceCardItem(
            title = "Remaining",
            amount = currency.format(remaining),
            progress = (remainingPercent / 100f).coerceIn(0f, 1f),
            progressColor = StatusGreen,
            progressText = "$remainingPercent%",
            testTag = "remaining_budget_card",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun FinanceCardItem(
    title: String,
    amount: String,
    progress: Float,
    progressColor: Color,
    progressText: String,
    testTag: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(horizontal = 10.dp, vertical = 12.dp)
            .testTag(testTag)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 11.sp
                ),
                maxLines = 1
            )
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                modifier = Modifier.size(14.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = amount,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = progressColor,
            trackColor = progressColor.copy(alpha = 0.15f),
            strokeCap = StrokeCap.Round
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = progressText,
            style = MaterialTheme.typography.labelSmall.copy(
                color = progressColor,
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp
            )
        )
    }
}
