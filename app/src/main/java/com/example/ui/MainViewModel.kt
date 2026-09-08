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
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = AppDatabase.getInstance(application)
    private val repository = TravellersRepository(database)

    init {
        // Seed mockup data if not already present
        AppDatabase.seedInitialData(database, viewModelScope)
        
        // Ensure default user profile exists so flows work in real-time
        viewModelScope.launch {
            try {
                val existing = repository.userProfile.first()
                if (existing == null) {
                    repository.saveProfile(
                        com.example.data.local.ProfileEntity(
                            id = 1,
                            name = "Rahul Shah",
                            age = "24",
                            gender = "Male",
                            photoUri = "",
                            bio = "Avid explorer and budget travel planner.",
                            passportNumber = "A12345678",
                            emergencyContact = "+1-555-0199",
                            homeAddress = "123 Adventure Lane, Horizon City",
                            bloodGroup = "O+",
                            customNote = "Keep hydration high. Prefers window seats."
                        )
                    )
                }
            } catch (e: Exception) {
                // Ignore errors
            }
        }
    }

    // App Preferences
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()

    private val _appTheme = MutableStateFlow(com.example.ui.theme.AppTheme.BURGUNDY)
    val appTheme: StateFlow<com.example.ui.theme.AppTheme> = _appTheme.asStateFlow()

    private val _isCompactMode = MutableStateFlow(false)
    val isCompactMode: StateFlow<Boolean> = _isCompactMode.asStateFlow()

    private val _isSolidMode = MutableStateFlow(false)
    val isSolidMode: StateFlow<Boolean> = _isSolidMode.asStateFlow()

    private val _appFont = MutableStateFlow(com.example.ui.theme.AppFont.SANS)
    val appFont: StateFlow<com.example.ui.theme.AppFont> = _appFont.asStateFlow()

    private val _customCornerRadiusDp = MutableStateFlow(-1) // -1 means default
    val customCornerRadiusDp: StateFlow<Int> = _customCornerRadiusDp.asStateFlow()

    private val _selectedCurrency = MutableStateFlow(AppCurrency.USD)
    val selectedCurrency: StateFlow<AppCurrency> = _selectedCurrency.asStateFlow()

    private val _hasSeenWelcome = MutableStateFlow(true) // Set to true to hide intro automatically
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
    val showInviteDialog = MutableStateFlow(false)
    val showPermissionsDialog = MutableStateFlow(false)
    val showThemeDialog = MutableStateFlow(false)

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

    fun setAppTheme(theme: com.example.ui.theme.AppTheme) {
        _appTheme.value = theme
        showThemeDialog.value = false
    }

    fun toggleCompactMode() {
        _isCompactMode.value = !_isCompactMode.value
    }

    fun setCompactMode(compact: Boolean) {
        _isCompactMode.value = compact
    }

    fun toggleSolidMode() {
        _isSolidMode.value = !_isSolidMode.value
    }

    fun setSolidMode(solid: Boolean) {
        _isSolidMode.value = solid
    }

    fun setAppFont(font: com.example.ui.theme.AppFont) {
        _appFont.value = font
    }

    fun setCustomCornerRadiusDp(dp: Int) {
        _customCornerRadiusDp.value = dp
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

    fun addCompanion(name: String, amount: Double = 0.0, tripId: Long? = null) {
        viewModelScope.launch {
            val targetTripId = tripId ?: _selectedTripId.value ?: allTrips.value.firstOrNull()?.id ?: 1L
            val cleanName = if (name.isBlank()) "Travel Companion" else name.trim()
            val initial = cleanName.take(1).uppercase()
            repository.addCompanion(
                CompanionEntity(
                    tripId = targetTripId,
                    name = cleanName,
                    initial = initial,
                    amount = amount,
                    status = "Pending",
                    isSelf = false
                )
            )
            showInviteDialog.value = false
        }
    }

    fun toggleCompanionStatus(companion: CompanionEntity) {
        viewModelScope.launch {
            val newStatus = if (companion.status == "Paid" || companion.status == "Settled") "Pending" else "Paid"
            repository.updateCompanion(companion.copy(status = newStatus))
        }
    }
}
