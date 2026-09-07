package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.components.AddExpenseDialog
import com.example.ui.components.AddTripDialog
import com.example.ui.components.EditBudgetDialog
import com.example.ui.components.FloatingBottomNavBar
import com.example.ui.components.NavigationTab
import com.example.ui.components.QuickActionsBottomSheet
import com.example.ui.screens.ExpensesScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MoreScreen
import com.example.ui.screens.SplitBillScreen
import com.example.ui.screens.TripDetailScreen
import com.example.ui.screens.TripsScreen
import com.example.ui.screens.WelcomeScreen
import com.example.ui.theme.TravellersTheme

@Composable
fun TravellersApp(
    viewModel: MainViewModel = viewModel()
) {
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    val appTheme by viewModel.appTheme.collectAsStateWithLifecycle()
    val isCompactMode by viewModel.isCompactMode.collectAsStateWithLifecycle()
    val hasSeenWelcome by viewModel.hasSeenWelcome.collectAsStateWithLifecycle()
    val currentCurrency by viewModel.selectedCurrency.collectAsStateWithLifecycle()

    var currentTab by remember { mutableStateOf(NavigationTab.HOME) }
    var activeTripDetailId by remember { mutableStateOf<Long?>(null) }
    var isViewingSplitBillDirectly by remember { mutableStateOf(false) }
    var isViewingProfile by remember { mutableStateOf(false) }

    // Dialog & sheet states
    val showQuickActions by viewModel.showQuickActions.collectAsStateWithLifecycle()
    val showAddTripDialog by viewModel.showAddTripDialog.collectAsStateWithLifecycle()
    val showAddExpenseDialog by viewModel.showAddExpenseDialog.collectAsStateWithLifecycle()
    val showEditBudgetDialog by viewModel.showEditBudgetDialog.collectAsStateWithLifecycle()

    TravellersTheme(darkTheme = isDarkMode, appTheme = appTheme) {
        androidx.compose.runtime.CompositionLocalProvider(
            com.example.ui.theme.LocalCompactMode provides isCompactMode
        ) {
            if (!hasSeenWelcome) {
            WelcomeScreen(
                onGetStarted = { viewModel.completeWelcome() }
            )
        } else {
            // Handle back button when inside detail views
            BackHandler(enabled = activeTripDetailId != null || isViewingSplitBillDirectly || isViewingProfile) {
                if (activeTripDetailId != null) {
                    activeTripDetailId = null
                } else if (isViewingSplitBillDirectly) {
                    isViewingSplitBillDirectly = false
                } else if (isViewingProfile) {
                    isViewingProfile = false
                }
            }

            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize()) {
                    // Screen Router
                    androidx.compose.animation.AnimatedContent(
                        targetState = Triple(activeTripDetailId, isViewingSplitBillDirectly, isViewingProfile),
                        label = "main_screen_transition",
                        transitionSpec = {
                            androidx.compose.animation.fadeIn() + androidx.compose.animation.scaleIn(initialScale = 0.95f) togetherWith
                            androidx.compose.animation.fadeOut() + androidx.compose.animation.scaleOut(targetScale = 0.95f)
                        }
                    ) { (tripId, showSplitBill, showProfile) ->
                        when {
                            tripId != null -> {
                                TripDetailScreen(
                                    tripId = tripId,
                                    viewModel = viewModel,
                                    onBackClick = { activeTripDetailId = null }
                                )
                            }
                            showSplitBill -> {
                                SplitBillScreen(
                                    viewModel = viewModel,
                                    onNavigateBack = { isViewingSplitBillDirectly = false }
                                )
                            }
                            showProfile -> {
                                com.example.ui.screens.ProfileSetupScreen(
                                    viewModel = viewModel,
                                    onNavigateBack = { isViewingProfile = false }
                                )
                            }

                        else -> {
                            androidx.compose.animation.AnimatedContent(
                                targetState = currentTab,
                                label = "tab_animation",
                                transitionSpec = {
                                    androidx.compose.animation.slideInHorizontally { width -> width } + androidx.compose.animation.fadeIn() togetherWith 
                                    androidx.compose.animation.slideOutHorizontally { width -> -width } + androidx.compose.animation.fadeOut()
                                }
                            ) { tab ->
                                when (tab) {
                                    NavigationTab.HOME -> {
                                        HomeScreen(
                                            viewModel = viewModel,
                                            onNavigateToTripDetail = { tripId ->
                                                viewModel.selectTrip(tripId)
                                                activeTripDetailId = tripId
                                            },
                                            onNavigateToSplitBill = {
                                                isViewingSplitBillDirectly = true
                                            },
                                            onNavigateToTrips = {
                                                currentTab = NavigationTab.TRIPS
                                            },
                                            onNavigateToProfile = {
                                                isViewingProfile = true
                                            }
                                        )
                                    }

                                    NavigationTab.TRIPS -> {
                                        TripsScreen(
                                            viewModel = viewModel,
                                            onTripClick = { tripId ->
                                                viewModel.selectTrip(tripId)
                                                activeTripDetailId = tripId
                                            }
                                        )
                                    }

                                    NavigationTab.EXPENSES -> {
                                        ExpensesScreen(
                                            viewModel = viewModel
                                        )
                                    }

                                    NavigationTab.MORE -> {
                                        MoreScreen(
                                            viewModel = viewModel,
                                            onNavigateToProfile = { 
                                                isViewingProfile = true
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                    }

                    // Floating Bottom Navigation Bar (Visible on primary tabs)
                    if (activeTripDetailId == null && !isViewingSplitBillDirectly && !isViewingProfile) {
                        FloatingBottomNavBar(
                            currentTab = currentTab,
                            onTabSelected = { currentTab = it },
                            onCenterPlusClick = { viewModel.showQuickActions.value = true },
                            modifier = Modifier.align(Alignment.BottomCenter)
                        )
                    }
                }
            }

            // Quick Actions Bottom Sheet
            if (showQuickActions) {
                QuickActionsBottomSheet(
                    onDismiss = { viewModel.showQuickActions.value = false },
                    onAddExpense = { viewModel.showAddExpenseDialog.value = true },
                    onNewTrip = { viewModel.showAddTripDialog.value = true },
                    onSplitBill = { isViewingSplitBillDirectly = true },
                    onInvite = { viewModel.showInviteDialog.value = true }
                )
            }

            // Invite Companion Dialog
            val showInviteDialog by viewModel.showInviteDialog.collectAsStateWithLifecycle()
            val allTrips by viewModel.allTrips.collectAsStateWithLifecycle()
            val selectedTripId by viewModel.selectedTripId.collectAsStateWithLifecycle()
            if (showInviteDialog) {
                com.example.ui.components.InviteCompanionDialog(
                    trips = allTrips,
                    initialTripId = selectedTripId,
                    onDismiss = { viewModel.showInviteDialog.value = false },
                    onAddCompanion = { name, amount, tripId ->
                        viewModel.addCompanion(name, amount, tripId)
                    }
                )
            }

            // Add Trip Dialog
            if (showAddTripDialog) {
                AddTripDialog(
                    onDismiss = { viewModel.showAddTripDialog.value = false },
                    onSaveTrip = { newTrip ->
                        viewModel.createTrip(newTrip)
                    },
                    currentCurrency = currentCurrency
                )
            }

            // Add Expense Dialog
            if (showAddExpenseDialog) {
                val currentTripId = viewModel.selectedTripId.value ?: 1L
                AddExpenseDialog(
                    tripId = currentTripId,
                    currentCurrency = currentCurrency,
                    onDismiss = { viewModel.showAddExpenseDialog.value = false },
                    onSaveExpense = { newExpense ->
                        viewModel.addExpense(newExpense)
                    }
                )
            }

            // Edit Budget Dialog
            if (showEditBudgetDialog) {
                val currentTripId = viewModel.selectedTripId.value ?: 1L
                val trip = viewModel.allTrips.value.firstOrNull { it.id == currentTripId }
                EditBudgetDialog(
                    currentBudget = trip?.totalBudget ?: 1200.0,
                    currency = currentCurrency,
                    onDismiss = { viewModel.showEditBudgetDialog.value = false },
                    onSave = { newBudget ->
                        viewModel.updateTripBudget(currentTripId, newBudget)
                    }
                )
            }
        }
    }
}
}

