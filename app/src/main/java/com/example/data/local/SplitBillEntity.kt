package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "split_bills")
data class SplitBillEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tripId: Long,
    val title: String,
    val totalAmount: Double,
    val perPersonAmount: Double,
    val dateDisplay: String,
    val currencyCode: String = "USD",
    val splitMethod: String = "EQUAL",
    val participantsList: String = "You,Alex,Sam,Priya",
    val status: String = "PENDING" // PENDING, SETTLED
)
