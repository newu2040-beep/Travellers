package com.example.data.local

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "profile")
data class ProfileEntity(
    @PrimaryKey val id: Int = 1,
    val name: String = "",
    val age: String = "",
    val gender: String = "",
    val photoUri: String = "",
    val bio: String = "",
    val passportNumber: String = "",
    val emergencyContact: String = "",
    val homeAddress: String = "",
    val bloodGroup: String = "",
    val customNote: String = ""
)

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profile WHERE id = 1")
    fun getProfile(): Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateProfile(profile: ProfileEntity)
}
