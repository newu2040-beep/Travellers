package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.ExpenseEntity
import com.example.data.local.TripEntity
import com.example.data.model.AppCurrency
import com.example.ui.MainViewModel
import com.example.ui.components.FinancialOverviewCards
import com.example.ui.components.NextTripFeaturedCard
import com.example.ui.components.QuickActionsRow
import com.example.ui.components.RecentTripItemCard
import com.example.ui.components.TravellersLogo
import com.example.ui.theme.BurgundyPrimary

@Composable
fun HomeScreen(
    viewModel: MainViewModel,
    onNavigateToTripDetail: (Long) -> Unit,
    onNavigateToSplitBill: () -> Unit,
    onNavigateToTrips: () -> Unit,
    modifier: Modifier = Modifier
) {
    val allTrips by viewModel.filteredTrips.collectAsStateWithLifecycle()
    val allExpenses by viewModel.allExpenses.collectAsStateWithLifecycle()
    val nextTrip by viewModel.nextTrip.collectAsStateWithLifecycle()
    val currentCurrency by viewModel.selectedCurrency.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()

    // Calculate spending metrics
    val featuredTrip = nextTrip ?: allTrips.firstOrNull()
    val featuredTripExpenses = if (featuredTrip != null) {
        allExpenses.filter { it.tripId == featuredTrip.id }
    } else emptyList()

    val totalSpent = featuredTripExpenses.sumOf { it.amount }
    val totalBudget = featuredTrip?.totalBudget ?: 1200.0

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 12.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Top Header: Avatar + Greeting + Logo & Theme Switcher
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Profile Avatar
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF14532D))
                            .border(1.5.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "R",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = "Hello, Traveller",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 17.sp
                            )
                        )
                        Text(
                            text = "Kathmandu valley awaits",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                fontSize = 12.sp
                            )
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Theme toggle button
                    IconButton(
                        onClick = { viewModel.toggleDarkMode() },
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface)
                            .testTag("home_theme_toggle_button")
                    ) {
                        Icon(
                            imageVector = if (isDarkMode) Icons.Default.LightMode else Icons.Default.DarkMode,
                            contentDescription = "Toggle Theme",
                            tint = BurgundyPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Circular Sailboat Logo
                    TravellersLogo(
                        size = 38.dp,
                        showBorder = true
                    )
                }
            }
        }

        // Search bar
        item {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                placeholder = {
                    Text(
                        "Search trips, expenses, places...",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            fontSize = 14.sp
                        )
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .testTag("home_search_bar"),
                shape = RoundedCornerShape(26.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BurgundyPrimary,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                    focusedContainerColor = MaterialTheme.colorScheme.surface,
                    unfocusedContainerColor = MaterialTheme.colorScheme.surface
                ),
                singleLine = true
            )
        }

        // Quick Actions Row (New Trip, Add Expense, Split Bill, Invite)
        item {
            QuickActionsRow(
                onNewTripClick = { viewModel.showAddTripDialog.value = true },
                onAddExpenseClick = { viewModel.showAddExpenseDialog.value = true },
                onSplitBillClick = onNavigateToSplitBill,
                onInviteClick = { viewModel.addCompanion("Friend") }
            )
        }

        // Next Trip Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Next Trip",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 17.sp
                    )
                )
                TextButton(
                    onClick = onNavigateToTrips,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "See all >",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = BurgundyPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        // Next Trip Featured Banner Card
        item {
            if (featuredTrip != null) {
                NextTripFeaturedCard(
                    trip = featuredTrip,
                    onTripClick = { onNavigateToTripDetail(it.id) }
                )
            }
        }

        // Financial Overview Cards (Budget, Spent, Remaining)
        item {
            FinancialOverviewCards(
                totalBudget = totalBudget,
                totalSpent = totalSpent,
                currency = currentCurrency
            )
        }

        // Recent Trips Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Recent Trips",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 17.sp
                    )
                )
                Text(
                    text = "All",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }

        // List of Recent Trips
        items(
            count = allTrips.size,
            key = { index -> allTrips[index].id }
        ) { index ->
            val trip = allTrips[index]
            val tripSpent = allExpenses.filter { it.tripId == trip.id }.sumOf { it.amount }
            RecentTripItemCard(
                trip = trip,
                spentAmount = tripSpent,
                currency = currentCurrency,
                onTripClick = { onNavigateToTripDetail(trip.id) }
            )
        }
    }
}
