package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AppCurrency
import com.example.ui.theme.BurgundyAccent
import com.example.ui.theme.BurgundyPrimary

@Composable
fun CircularProgressGauge(
    spentAmount: Double,
    totalBudget: Double,
    currency: AppCurrency,
    onEditBudgetClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = 100.dp,
    strokeWidth: Dp = 10.dp
) {
    val progress = if (totalBudget > 0) (spentAmount / totalBudget).toFloat().coerceIn(0f, 1f) else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 800),
        label = "gaugeProgress"
    )
    val percentage = (progress * 100).toInt()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(18.dp)
            .testTag("budget_progress_card"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        // Circular ring
        Box(
            modifier = Modifier.size(size),
            contentAlignment = Alignment.Center
        ) {
            val trackColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            val progressColor = if (progress > 0.9f) BurgundyAccent else BurgundyPrimary

            Canvas(modifier = Modifier.size(size)) {
                val strokePx = strokeWidth.toPx()
                val radius = (this.size.minDimension - strokePx) / 2
                val centerOffset = center

                // Background track
                drawCircle(
                    color = trackColor,
                    radius = radius,
                    center = centerOffset,
                    style = Stroke(width = strokePx)
                )

                // Animated progress arc
                drawArc(
                    color = progressColor,
                    startAngle = -90f,
                    sweepAngle = 360f * animatedProgress,
                    useCenter = false,
                    style = Stroke(width = strokePx, cap = StrokeCap.Round)
                )
            }

            Text(
                text = "$percentage%",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        Spacer(modifier = Modifier.width(20.dp))

        // Text & Actions
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Budget Used",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${currency.format(spentAmount)} / ${currency.format(totalBudget)}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 18.sp
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            ElevatedButton(
                onClick = onEditBudgetClick,
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = BurgundyPrimary.copy(alpha = 0.12f),
                    contentColor = BurgundyPrimary
                ),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                modifier = Modifier
                    .height(34.dp)
                    .testTag("edit_budget_button")
            ) {
                Text(
                    text = "Edit Budget",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}
