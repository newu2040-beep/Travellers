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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CallSplit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.local.SplitBillEntity
import com.example.data.local.TripEntity
import com.example.ui.MainViewModel
import com.example.ui.components.ParticipantAvatarCard
import com.example.ui.components.RecentSplitItemRow
import com.example.ui.theme.BurgundyDark
import com.example.ui.theme.BurgundyPrimary
import com.example.ui.theme.PastelMint
import com.example.ui.theme.PastelPeach
import com.example.ui.theme.StatusGreen
import com.example.ui.theme.StatusOwed

@Composable
fun SplitBillScreen(
    viewModel: MainViewModel,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val allTrips by viewModel.allTrips.collectAsStateWithLifecycle()
    val allSplitBills by viewModel.allSplitBills.collectAsStateWithLifecycle()
    val allCompanions by viewModel.allCompanions.collectAsStateWithLifecycle()
    val currentCurrency by viewModel.selectedCurrency.collectAsStateWithLifecycle()
    val selectedTripId by viewModel.selectedTripId.collectAsStateWithLifecycle()

    val currentTrip = allTrips.firstOrNull { it.id == selectedTripId } ?: allTrips.firstOrNull()
    val tripSplits = if (currentTrip != null) {
        allSplitBills.filter { it.tripId == currentTrip.id }
    } else allSplitBills

    val tripCompanions = if (currentTrip != null) {
        allCompanions.filter { it.tripId == currentTrip.id }
    } else allCompanions

    var showTripDropdown by remember { mutableStateOf(false) }
    var showAddCompanionDialog by remember { mutableStateOf(false) }
    var showNewSplitDialog by remember { mutableStateOf(false) }

    val pendingTotal = tripCompanions.filter { it.status == "Pending" }.sumOf { it.amount }
    val youAreOwed = tripCompanions.filter { !it.isSelf && it.status == "Pending" }.sumOf { it.amount }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        contentPadding = PaddingValues(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 100.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        // Header with Trip Selector Pill & Add Companion Action
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Split Bill",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 20.sp
                        )
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    // Trip selector pill dropdown
                    Box {
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.surface)
                                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                                .clickable { showTripDropdown = true }
                                .padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "${currentTrip?.destination ?: "All Trips"}, ${currentTrip?.country ?: ""}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = BurgundyPrimary,
                                    fontSize = 12.sp
                                )
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = "Select Trip",
                                tint = BurgundyPrimary,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        DropdownMenu(
                            expanded = showTripDropdown,
                            onDismissRequest = { showTripDropdown = false }
                        ) {
                            allTrips.forEach { trip ->
                                DropdownMenuItem(
                                    text = { Text("${trip.destination} (${trip.durationDays} days)") },
                                    onClick = {
                                        viewModel.selectTrip(trip.id)
                                        showTripDropdown = false
                                    }
                                )
                            }
                        }
                    }
                }

                // Add companion button
                IconButton(
                    onClick = { showAddCompanionDialog = true },
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface)
                        .testTag("add_companion_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.PersonAdd,
                        contentDescription = "Add Companion",
                        tint = BurgundyPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }

        // Outstanding Balances Summary Card
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.25f), RoundedCornerShape(24.dp))
                    .padding(18.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Total Pending",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = currentCurrency.format(pendingTotal),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 22.sp
                        )
                    )
                }

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(PastelMint)
                        .padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "You are owed",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = StatusGreen,
                                fontSize = 11.sp
                            )
                        )
                        Text(
                            text = currentCurrency.format(youAreOwed),
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = StatusGreen,
                                fontSize = 16.sp
                            )
                        )
                    }
                }
            }
        }

        // Participants Section Header
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Participants (${tripCompanions.size})",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 17.sp
                    )
                )
                TextButton(
                    onClick = { showAddCompanionDialog = true },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "+ Add",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = BurgundyPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        // Participants Grid/Row (You, Alex, Sam, Priya)
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tripCompanions.take(4).forEach { companion ->
                    ParticipantAvatarCard(
                        companion = companion,
                        currency = currentCurrency,
                        onClick = { viewModel.toggleCompanionStatus(companion) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // Recent Split Activity Header + Action
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Recent Split Activity",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontSize = 17.sp
                    )
                )
                TextButton(
                    onClick = { showNewSplitDialog = true },
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "+ Split a Bill",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = BurgundyPrimary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }

        // List of Split Bills (Lunch, Taxi, Hotel, etc.)
        items(
            count = tripSplits.size,
            key = { index -> tripSplits[index].id }
        ) { index ->
            val split = tripSplits[index]
            RecentSplitItemRow(
                split = split,
                currency = currentCurrency,
                onSplitClick = { viewModel.toggleSplitStatus(split) }
            )
        }
    }

    // Add Companion Dialog
    if (showAddCompanionDialog) {
        var companionName by remember { mutableStateOf("") }
        var companionAmount by remember { mutableStateOf("50") }

        Dialog(onDismissRequest = { showAddCompanionDialog = false }) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Add Travel Companion",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    OutlinedTextField(
                        value = companionName,
                        onValueChange = { companionName = it },
                        label = { Text("Friend's Name") },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth().testTag("input_companion_name")
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = companionAmount,
                        onValueChange = { companionAmount = it },
                        label = { Text("Initial Balance (${currentCurrency.symbol})") },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            if (companionName.isNotBlank()) {
                                viewModel.addCompanion(companionName, companionAmount.toDoubleOrNull() ?: 0.0)
                            }
                            showAddCompanionDialog = false
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BurgundyPrimary)
                    ) {
                        Text("Add to Trip", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }

    // Split New Bill Dialog
    if (showNewSplitDialog) {
        var billTitle by remember { mutableStateOf("") }
        var totalAmountText by remember { mutableStateOf("") }
        var peopleCount by remember { mutableStateOf("4") }

        Dialog(onDismissRequest = { showNewSplitDialog = false }) {
            Surface(
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Split a New Bill",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    OutlinedTextField(
                        value = billTitle,
                        onValueChange = { billTitle = it },
                        label = { Text("Description (e.g. Dinner, Taxi, Museum)") },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth().testTag("input_split_title")
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = totalAmountText,
                        onValueChange = { totalAmountText = it },
                        label = { Text("Total Bill Amount (${currentCurrency.symbol})") },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth().testTag("input_split_amount")
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    OutlinedTextField(
                        value = peopleCount,
                        onValueChange = { peopleCount = it },
                        label = { Text("Number of People") },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                    Button(
                        onClick = {
                            val total = totalAmountText.toDoubleOrNull() ?: 0.0
                            val count = peopleCount.toIntOrNull()?.coerceAtLeast(1) ?: 1
                            val perPerson = total / count
                            viewModel.addSplitBill(
                                SplitBillEntity(
                                    tripId = currentTrip?.id ?: 1L,
                                    title = billTitle.ifBlank { "Group Expense" },
                                    totalAmount = total,
                                    perPersonAmount = perPerson,
                                    dateDisplay = "Today",
                                    currencyCode = currentCurrency.code,
                                    splitMethod = "EQUAL",
                                    status = "PENDING"
                                )
                            )
                            showNewSplitDialog = false
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BurgundyPrimary)
                    ) {
                        Text("Calculate & Split", color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
