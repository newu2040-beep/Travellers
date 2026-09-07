package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class TripEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val destination: String,
    val country: String = "Nepal",
    val startDate: String,
    val endDate: String,
    val durationDays: Int,
    val currencyCode: String = "USD",
    val totalBudget: Double,
    val coverImageRes: Int = 0,
    val notes: String = "",
    val status: String = "ACTIVE", // ACTIVE, UPCOMING, COMPLETED
    val travellersCount: Int = 2
)
