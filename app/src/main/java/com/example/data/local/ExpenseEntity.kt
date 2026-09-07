package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tripId: Long,
    val title: String,
    val amount: Double,
    val currencyCode: String = "USD",
    val category: String = "Other",
    val dateDisplay: String,
    val payerName: String = "You",
    val participantsCount: Int = 2,
    val notes: String = ""
)
