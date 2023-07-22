package com.ksas.maintac.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Create a new table with the updated column types
        database.execSQL(
            "CREATE TABLE new_Rent (" +
                    "rentId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                    "owner BLOB NOT NULL," + // Use BLOB to store custom data classes
                    "year INTEGER NOT NULL," + // Use INTEGER to store Year type
                    "month INTEGER NOT NULL," + // Use INTEGER to store Month type
                    "amount BLOB NOT NULL)"
        ) // Use BLOB to store custom data classes

        // Copy data from the old table to the new one while converting custom data classes
        database.execSQL(
            "INSERT INTO new_Rent (rentId, owner, year, month, amount) " +
                    "SELECT rentId, owner, CAST(year AS INTEGER) AS year, CAST(month AS INTEGER) AS month, amount FROM Rent"
        )

        // Drop the old table and rename the new one to match the original table name
        database.execSQL("DROP TABLE Rent")
        database.execSQL("ALTER TABLE new_Rent RENAME TO Rent")
    }
}

