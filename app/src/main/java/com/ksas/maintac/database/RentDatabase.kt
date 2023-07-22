package com.ksas.maintac.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ksas.maintac.model.Owner
import com.ksas.maintac.model.Rent

@Database(entities = [Rent::class, Owner::class], version = 1, exportSchema = false)
abstract class RentDatabase : RoomDatabase() {
    abstract fun rentDao(): RentDao

}