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
    androidx.compose.foundation.layout.Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        QuickActionButton(
            title = "New Trip",
            icon = Icons.Default.NearMe,
            bgColor = PastelBlush,
            iconTint = BurgundyPrimary,
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
    Column(
        modifier = modifier
            .shadow(2.dp, shape = RoundedCornerShape(18.dp), spotColor = Color.Black.copy(alpha = 0.05f))
            .clip(RoundedCornerShape(18.dp))
            .background(MaterialTheme.colorScheme.surface)
            .clickable(
                interactionSource = null,
                indication = ripple(bounded = true),
                onClick = onClick
            )
            .padding(vertical = 12.dp, horizontal = 4.dp)
            .testTag(testTag),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(bgColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier.padding(top = 6.dp),
            maxLines = 1
        )
    }
}
