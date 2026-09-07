package com.example.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanionDao {
    @Query("SELECT * FROM companions WHERE tripId = :tripId ORDER BY isSelf DESC, id ASC")
    fun getCompanionsForTrip(tripId: Long): Flow<List<CompanionEntity>>

    @Query("SELECT * FROM companions ORDER BY id ASC")
    fun getAllCompanions(): Flow<List<CompanionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanion(companion: CompanionEntity): Long

    @Update
    suspend fun updateCompanion(companion: CompanionEntity)

    @Delete
    suspend fun deleteCompanion(companion: CompanionEntity)
}
