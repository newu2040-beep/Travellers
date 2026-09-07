package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Work
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.ReceiptLong
import androidx.compose.material.icons.outlined.WorkOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BurgundyPrimary

enum class NavigationTab(val title: String) {
    HOME("Home"),
    TRIPS("Trips"),
    EXPENSES("Expenses"),
    MORE("More")
}

@Composable
fun FloatingBottomNavBar(
    currentTab: NavigationTab,
    onTabSelected: (NavigationTab) -> Unit,
    onCenterPlusClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        // Floating pill bar
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .shadow(
                    elevation = 12.dp,
                    shape = RoundedCornerShape(32.dp),
                    spotColor = Color.Black.copy(alpha = 0.15f)
                ),
            shape = RoundedCornerShape(32.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            border = androidx.compose.foundation.BorderStroke(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Home
                NavTabItem(
                    tab = NavigationTab.HOME,
                    isSelected = currentTab == NavigationTab.HOME,
                    selectedIcon = Icons.Filled.Home,
                    unselectedIcon = Icons.Outlined.Home,
                    onSelect = { onTabSelected(NavigationTab.HOME) }
                )

                // Trips
                NavTabItem(
                    tab = NavigationTab.TRIPS,
                    isSelected = currentTab == NavigationTab.TRIPS,
                    selectedIcon = Icons.Filled.Work,
                    unselectedIcon = Icons.Outlined.WorkOutline,
                    onSelect = { onTabSelected(NavigationTab.TRIPS) }
                )

                // Space for floating center plus button
                Box(modifier = Modifier.size(52.dp))

                // Expenses
                NavTabItem(
                    tab = NavigationTab.EXPENSES,
                    isSelected = currentTab == NavigationTab.EXPENSES,
                    selectedIcon = Icons.Filled.Receipt,
                    unselectedIcon = Icons.Outlined.ReceiptLong,
                    onSelect = { onTabSelected(NavigationTab.EXPENSES) }
                )

                // More
                NavTabItem(
                    tab = NavigationTab.MORE,
                    isSelected = currentTab == NavigationTab.MORE,
                    selectedIcon = Icons.Filled.Menu,
                    unselectedIcon = Icons.Outlined.Menu,
                    onSelect = { onTabSelected(NavigationTab.MORE) }
                )
            }
        }

        // Floating Central + Button
        Box(
            modifier = Modifier
                .offset(y = (-14).dp)
                .size(54.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = CircleShape,
                    spotColor = BurgundyPrimary.copy(alpha = 0.4f)
                )
                .clip(CircleShape)
                .background(BurgundyPrimary)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(bounded = true, color = Color.White),
                    onClick = onCenterPlusClick
                )
                .testTag("floating_center_add_button"),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Quick Add Actions",
                tint = Color.White,
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
private fun NavTabItem(
    tab: NavigationTab,
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    onSelect: () -> Unit
) {
    val iconColor by animateColorAsState(
        targetValue = if (isSelected) BurgundyPrimary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
        label = "navIconColor"
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple(bounded = true),
                onClick = onSelect
            )
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .testTag("nav_tab_${tab.name.lowercase()}"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = if (isSelected) selectedIcon else unselectedIcon,
            contentDescription = tab.title,
            tint = iconColor,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = tab.title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                color = iconColor
            )
        )
    }
}
