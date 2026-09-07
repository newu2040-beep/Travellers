package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface SplitBillDao {
    @Query("SELECT * FROM split_bills ORDER BY id DESC")
    fun getAllSplitBills(): Flow<List<SplitBillEntity>>

    @Query("SELECT * FROM split_bills WHERE tripId = :tripId ORDER BY id DESC")
    fun getSplitBillsForTrip(tripId: Long): Flow<List<SplitBillEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSplitBill(split: SplitBillEntity): Long

    @Update
    suspend fun updateSplitBill(split: SplitBillEntity)

    @Delete
    suspend fun deleteSplitBill(split: SplitBillEntity)

    @Query("DELETE FROM split_bills WHERE id = :id")
    suspend fun deleteSplitBillById(id: Long)
}
