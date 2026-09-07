package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.CompanionEntity
import com.example.data.local.ExpenseEntity
import com.example.data.local.SplitBillEntity
import com.example.data.local.TripEntity
import kotlinx.coroutines.flow.Flow

class TravellersRepository(private val database: AppDatabase) {
    val allTrips: Flow<List<TripEntity>> = database.tripDao().getAllTrips()
    val allExpenses: Flow<List<ExpenseEntity>> = database.expenseDao().getAllExpenses()
    val allSplitBills: Flow<List<SplitBillEntity>> = database.splitBillDao().getAllSplitBills()
    val allCompanions: Flow<List<CompanionEntity>> = database.companionDao().getAllCompanions()
    val userProfile = database.profileDao().getProfile()

    fun getTripById(tripId: Long): Flow<TripEntity?> = database.tripDao().getTripById(tripId)
    fun getExpensesForTrip(tripId: Long): Flow<List<ExpenseEntity>> = database.expenseDao().getExpensesForTrip(tripId)
    fun getSplitBillsForTrip(tripId: Long): Flow<List<SplitBillEntity>> = database.splitBillDao().getSplitBillsForTrip(tripId)
    fun getCompanionsForTrip(tripId: Long): Flow<List<CompanionEntity>> = database.companionDao().getCompanionsForTrip(tripId)

    suspend fun createTrip(trip: TripEntity): Long = database.tripDao().insertTrip(trip)
    suspend fun updateTrip(trip: TripEntity) = database.tripDao().updateTrip(trip)
    suspend fun deleteTrip(tripId: Long) = database.tripDao().deleteTripById(tripId)

    suspend fun addExpense(expense: ExpenseEntity): Long = database.expenseDao().insertExpense(expense)
    suspend fun updateExpense(expense: ExpenseEntity) = database.expenseDao().updateExpense(expense)
    suspend fun deleteExpense(expenseId: Long) = database.expenseDao().deleteExpenseById(expenseId)

    suspend fun addSplitBill(splitBill: SplitBillEntity): Long = database.splitBillDao().insertSplitBill(splitBill)
    suspend fun updateSplitBill(splitBill: SplitBillEntity) = database.splitBillDao().updateSplitBill(splitBill)
    suspend fun deleteSplitBill(splitBillId: Long) = database.splitBillDao().deleteSplitBillById(splitBillId)

    suspend fun addCompanion(companion: CompanionEntity): Long = database.companionDao().insertCompanion(companion)
    suspend fun updateCompanion(companion: CompanionEntity) = database.companionDao().updateCompanion(companion)
    suspend fun deleteCompanion(companion: CompanionEntity) = database.companionDao().deleteCompanion(companion)
    
    suspend fun saveProfile(profile: com.example.data.local.ProfileEntity) = database.profileDao().insertOrUpdateProfile(profile)
}
