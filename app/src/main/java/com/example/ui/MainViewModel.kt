package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.CompanionEntity
import com.example.data.local.ExpenseEntity
import com.example.data.local.SplitBillEntity
import com.example.data.local.TripEntity
import com.example.data.model.AppCurrency
import com.example.data.repository.TravellersRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getInstance(application)
    private val repository = TravellersRepository(database)

    init {
        // Seed mockup data if not already present
        AppDatabase.seedInitialData(database, viewModelScope)
    }

    // App Preferences
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _selectedCurrency = MutableStateFlow(AppCurrency.USD)
    val selectedCurrency: StateFlow<AppCurrency> = _selectedCurrency.asStateFlow()

    private val _hasSeenWelcome = MutableStateFlow(false)
    val hasSeenWelcome: StateFlow<Boolean> = _hasSeenWelcome.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedTripId = MutableStateFlow<Long?>(1L)
    val selectedTripId: StateFlow<Long?> = _selectedTripId.asStateFlow()

    // Dialog & Navigation states
    val showQuickActions = MutableStateFlow(false)
    val showAddTripDialog = MutableStateFlow(false)
    val showAddExpenseDialog = MutableStateFlow(false)
    val showEditBudgetDialog = MutableStateFlow(false)

    // Data streams from Room
    val allTrips: StateFlow<List<TripEntity>> = repository.allTrips
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allExpenses: StateFlow<List<ExpenseEntity>> = repository.allExpenses
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allSplitBills: StateFlow<List<SplitBillEntity>> = repository.allSplitBills
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allCompanions: StateFlow<List<CompanionEntity>> = repository.allCompanions
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userProfile: StateFlow<com.example.data.local.ProfileEntity?> = repository.userProfile
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun saveProfile(profile: com.example.data.local.ProfileEntity) = viewModelScope.launch {
        repository.saveProfile(profile)
    }

    // Filtered trips based on search
    val filteredTrips: StateFlow<List<TripEntity>> = combine(allTrips, _searchQuery) { trips, query ->
        if (query.isBlank()) trips
        else trips.filter {
            it.destination.contains(query, ignoreCase = true) ||
                    it.country.contains(query, ignoreCase = true) ||
                    it.notes.contains(query, ignoreCase = true)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active/Next trip (Kathmandu or first available)
    val nextTrip: StateFlow<TripEntity?> = combine(allTrips, _selectedTripId) { trips, selectedId ->
        trips.firstOrNull { it.id == selectedId } ?: trips.firstOrNull()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    // Actions
    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }

    fun setDarkMode(dark: Boolean) {
        _isDarkMode.value = dark
    }

    fun setCurrency(currency: AppCurrency) {
        _selectedCurrency.value = currency
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun selectTrip(tripId: Long) {
        _selectedTripId.value = tripId
    }

    fun completeWelcome() {
        _hasSeenWelcome.value = true
    }

    fun resetWelcome() {
        _hasSeenWelcome.value = false
    }

    // CRUD operations
    fun createTrip(trip: TripEntity) {
        viewModelScope.launch {
            val id = repository.createTrip(trip)
            _selectedTripId.value = id
            showAddTripDialog.value = false
        }
    }

    fun updateTripBudget(tripId: Long, newBudget: Double) {
        viewModelScope.launch {
            val trip = allTrips.value.firstOrNull { it.id == tripId }
            if (trip != null) {
                repository.updateTrip(trip.copy(totalBudget = newBudget))
            }
            showEditBudgetDialog.value = false
        }
    }

    fun deleteTrip(tripId: Long) {
        viewModelScope.launch {
            repository.deleteTrip(tripId)
            if (_selectedTripId.value == tripId) {
                _selectedTripId.value = allTrips.value.firstOrNull { it.id != tripId }?.id
            }
        }
    }

    fun addExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.addExpense(expense)
            showAddExpenseDialog.value = false
        }
    }

    fun deleteExpense(expenseId: Long) {
        viewModelScope.launch {
            repository.deleteExpense(expenseId)
        }
    }

    fun addSplitBill(splitBill: SplitBillEntity) {
        viewModelScope.launch {
            repository.addSplitBill(splitBill)
        }
    }

    fun toggleSplitStatus(splitBill: SplitBillEntity) {
        viewModelScope.launch {
            val newStatus = if (splitBill.status == "SETTLED") "PENDING" else "SETTLED"
            repository.updateSplitBill(splitBill.copy(status = newStatus))
        }
    }

    fun addCompanion(name: String, amount: Double = 0.0) {
        viewModelScope.launch {
            val currentTripId = _selectedTripId.value ?: 1L
            val initial = name.trim().take(1).uppercase()
            repository.addCompanion(
                CompanionEntity(
                    tripId = currentTripId,
                    name = name.trim(),
                    initial = initial,
                    amount = amount,
                    status = "Pending",
                    isSelf = false
                )
            )
        }
    }

    fun toggleCompanionStatus(companion: CompanionEntity) {
        viewModelScope.launch {
            val newStatus = if (companion.status == "Paid" || companion.status == "Settled") "Pending" else "Paid"
            repository.updateCompanion(companion.copy(status = newStatus))
        }
    }
}
