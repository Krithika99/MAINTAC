package com.ksas.maintac.database

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE: RentDatabase? = null
    fun getInstance(context: Context): RentDatabase {
        if (INSTANCE == null) {
            synchronized(RentDatabase::class) {
                INSTANCE = buildRoomDb(context)
            }
        }

        return INSTANCE!!
    }

    private fun buildRoomDb(context: Context): RentDatabase? {
        return Room.databaseBuilder(context, RentDatabase::class.java, "Rent").build()
    }
}