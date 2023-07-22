package com.ksas.maintac.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ksas.maintac.converter.MonthTypeConverter
import com.ksas.maintac.converter.OwnerTypeConverter
import com.ksas.maintac.converter.YearTypeConverter
import com.ksas.maintac.model.Owner
import com.ksas.maintac.model.Rent

@Database(entities = [Rent::class, Owner::class], version = 2, exportSchema = false)
@TypeConverters(YearTypeConverter::class, MonthTypeConverter::class, OwnerTypeConverter::class)
abstract class RentDatabase : RoomDatabase() {
    abstract fun rentDao(): RentDao

}