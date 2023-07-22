package com.ksas.maintac.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ksas.maintac.model.Rent

@Dao
interface RentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRentDetails(rent: Rent)

    @Query("Select * from Rent")
    suspend fun getRentDetails(): List<Rent>

    @Query("Select * from Rent where year == :year")
    suspend fun getRentDetailsByYear(year: String): List<Rent>

}