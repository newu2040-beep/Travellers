package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.FlightTakeoff
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.MainViewModel
import com.example.ui.components.NextTripFeaturedCard
import com.example.ui.components.RecentTripItemCard
import com.example.ui.theme.BurgundyPrimary

@Composable
fun TripsScreen(
    viewModel: MainViewModel,
    onTripClick: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val allTrips by viewModel.allTrips.collectAsStateWithLifecycle()
    val allExpenses by viewModel.allExpenses.collectAsStateWithLifecycle()
    val currentCurrency by viewModel.selectedCurrency.collectAsStateWithLifecycle()
    val isCompact = com.example.ui.theme.LocalCompactMode.current
    val primaryColor = MaterialTheme.colorScheme.primary

    var filterStatus by remember { mutableStateOf("ALL") }

    val filtered = when (filterStatus) {
        "ACTIVE" -> allTrips.filter { it.status.equals("ACTIVE", ignoreCase = true) }
        "UPCOMING" -> allTrips.filter { it.status.equals("UPCOMING", ignoreCase = true) }
        "COMPLETED" -> allTrips.filter { it.status.equals("COMPLETED", ignoreCase = true) }
        else -> allTrips
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        contentPadding = PaddingValues(
            start = if (isCompact) 14.dp else 20.dp,
            end = if (isCompact) 14.dp else 20.dp,
            top = 12.dp,
            bottom = 100.dp
        ),
        verticalArrangement = Arrangement.spacedBy(if (isCompact) 12.dp else 16.dp)
    ) {
        // Title Bar + Create Button
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "My Trips",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = if (isCompact) 20.sp else 24.sp
                    )
                )

                IconButton(
                    onClick = { viewModel.showAddTripDialog.value = true },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(primaryColor)
                        .testTag("trips_screen_create_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "New Trip",
                        tint = Color.White
                    )
                }
            }
        }

        // Filter chips
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(
                    Triple("ALL", "All Trips", Icons.Default.Explore),
                    Triple("ACTIVE", "Active", Icons.Default.FlightTakeoff),
                    Triple("UPCOMING", "Upcoming", Icons.Default.CalendarToday),
                    Triple("COMPLETED", "Past", Icons.Default.CheckCircle)
                ).forEach { (code, label, icon) ->
                    FilterChip(
                        selected = filterStatus == code,
                        onClick = { filterStatus = code },
                        label = { Text(label, fontSize = 12.sp) },
                        leadingIcon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = primaryColor,
                            selectedLabelColor = Color.White
                        )
                    )
                }
            }
        }

        // Featured First Trip
        if (filtered.isNotEmpty()) {
            item {
                Text(
                    text = "Featured Journey",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                NextTripFeaturedCard(
                    trip = filtered.first(),
                    onTripClick = { onTripClick(it.id) }
                )
            }
        }

        // Other trips list
        items(
            count = filtered.size,
            key = { index -> filtered[index].id }
        ) { index ->
            val trip = filtered[index]
            val tripSpent = allExpenses.filter { it.tripId == trip.id }.sumOf { it.amount }
            RecentTripItemCard(
                trip = trip,
                spentAmount = tripSpent,
                currency = currentCurrency,
                onTripClick = { onTripClick(trip.id) }
            )
        }
    }
}
