package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.CompanionEntity
import com.example.data.local.SplitBillEntity
import com.example.data.model.AppCurrency
import com.example.ui.theme.BurgundyDark
import com.example.ui.theme.BurgundyPrimary
import com.example.ui.theme.PastelBlush
import com.example.ui.theme.PastelMint
import com.example.ui.theme.PastelPeach
import com.example.ui.theme.PastelSoftYellow
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.StatusGreenBg
import com.example.ui.theme.StatusPending
import com.example.ui.theme.StatusPendingBg

@Composable
fun ParticipantAvatarCard(
    companion: CompanionEntity,
    currency: AppCurrency,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isPaid = companion.status.equals("Paid", ignoreCase = true) || companion.status.equals("Settled", ignoreCase = true)
    val statusBg = if (isPaid) StatusGreenBg else StatusPendingBg
    val statusColor = if (isPaid) StatusGreen else StatusPending

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .testTag("companion_avatar_${companion.name.lowercase()}"),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Avatar circle
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(Color(companion.avatarBgHex)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = companion.initial,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (companion.isSelf) Color.White else BurgundyDark
                )
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = companion.name,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = currency.format(companion.amount),
            style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 11.sp
            )
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Status pill
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(statusBg)
                .padding(horizontal = 8.dp, vertical = 2.dp)
        ) {
            Text(
                text = companion.status,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = statusColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 10.sp
                )
            )
        }
    }
}

@Composable
fun RecentSplitItemRow(
    split: SplitBillEntity,
    currency: AppCurrency,
    onSplitClick: (SplitBillEntity) -> Unit,
    modifier: Modifier = Modifier
) {
    val (icon, bgColor) = when {
        split.title.contains("lunch", ignoreCase = true) || split.title.contains("food", ignoreCase = true) ->
            Icons.Default.Restaurant to PastelPeach
        split.title.contains("taxi", ignoreCase = true) || split.title.contains("ride", ignoreCase = true) ->
            Icons.Default.DirectionsCar to PastelSoftYellow
        split.title.contains("hotel", ignoreCase = true) ->
            Icons.Default.Hotel to PastelMint
        else -> Icons.Default.Restaurant to PastelBlush
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable { onSplitClick(split) }
            .padding(14.dp)
            .testTag("split_item_${split.id}"),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = split.title,
                tint = BurgundyDark,
                modifier = Modifier.size(22.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = split.title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 15.sp
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${split.dateDisplay} · ${currency.format(split.totalAmount)}",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = StatusGreen,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = "Split equally",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = StatusGreen,
                        fontSize = 11.sp
                    )
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${currency.format(split.perPersonAmount)} each",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = BurgundyPrimary,
                    fontSize = 14.sp
                )
            )
            Spacer(modifier = Modifier.height(6.dp))
            // Mini participant avatar stack
            Row(horizontalArrangement = Arrangement.spacedBy((-6).dp)) {
                listOf("R" to Color(0xFF14532D), "A" to Color(0xFFE11D48), "S" to Color(0xFFD97706), "P" to Color(0xFF2563EB)).forEach { (init, col) ->
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(col)
                            .border(1.5.dp, MaterialTheme.colorScheme.surface, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = init,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}
