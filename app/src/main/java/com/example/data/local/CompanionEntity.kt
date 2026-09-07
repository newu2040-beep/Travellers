package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companions")
data class CompanionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val tripId: Long,
    val name: String,
    val initial: String,
    val amount: Double,
    val status: String = "Pending", // Paid, Pending, Settled, Owes you, You owe
    val isSelf: Boolean = false,
    val avatarBgHex: Long = 0xFFFDE8E9
)
