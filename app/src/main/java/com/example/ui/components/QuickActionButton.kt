package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CallSplit
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BurgundyPrimary
import com.example.ui.theme.PastelBlush
import com.example.ui.theme.PastelLavender
import com.example.ui.theme.PastelMint
import com.example.ui.theme.PastelPeach

@Composable
fun QuickActionsRow(
    onNewTripClick: () -> Unit,
    onAddExpenseClick: () -> Unit,
    onSplitBillClick: () -> Unit,
    onInviteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryThemeColor = MaterialTheme.colorScheme.primary
    val isCompact = com.example.ui.theme.LocalCompactMode.current
    val space = if (isCompact) 6.dp else 10.dp

    androidx.compose.foundation.layout.Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(space)
    ) {
        QuickActionButton(
            title = "New Trip",
            icon = Icons.Default.NearMe,
            bgColor = PastelBlush,
            iconTint = primaryThemeColor,
            onClick = onNewTripClick,
            testTag = "quick_action_new_trip",
            modifier = Modifier.weight(1f)
        )
        QuickActionButton(
            title = "Add Expense",
            icon = Icons.Default.CreditCard,
            bgColor = PastelPeach,
            iconTint = Color(0xFFC2410C),
            onClick = onAddExpenseClick,
            testTag = "quick_action_add_expense",
            modifier = Modifier.weight(1f)
        )
        QuickActionButton(
            title = "Split Bill",
            icon = Icons.Default.CallSplit,
            bgColor = PastelMint,
            iconTint = Color(0xFF15803D),
            onClick = onSplitBillClick,
            testTag = "quick_action_split_bill",
            modifier = Modifier.weight(1f)
        )
        QuickActionButton(
            title = "Invite",
            icon = Icons.Default.PersonAdd,
            bgColor = PastelLavender,
            iconTint = Color(0xFF6D28D9),
            onClick = onInviteClick,
            testTag = "quick_action_invite",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun QuickActionButton(
    title: String,
    icon: ImageVector,
    bgColor: Color,
    iconTint: Color,
    onClick: () -> Unit,
    testTag: String,
    modifier: Modifier = Modifier
) {
    val isCompact = com.example.ui.theme.LocalCompactMode.current
    val vertPadding = if (isCompact) 8.dp else 12.dp
    val iconBoxSize = if (isCompact) 36.dp else 42.dp
    val iconSize = if (isCompact) 18.dp else 20.dp
    val textFontSize = if (isCompact) 10.sp else 11.sp

    LiquidGlassCard(
        modifier = modifier.testTag(testTag),
        shape = RoundedCornerShape(18.dp),
        elevation = 3.dp,
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = vertPadding, horizontal = 2.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(iconBoxSize)
                    .clip(RoundedCornerShape(12.dp))
                    .background(bgColor),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(iconSize)
                )
            }
            Text(
                text = title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = textFontSize,
                    color = MaterialTheme.colorScheme.onSurface
                ),
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 1
            )
        }
    }
}
