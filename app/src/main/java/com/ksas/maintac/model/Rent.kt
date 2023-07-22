package com.ksas.maintac.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Month
import java.time.Year

@Entity(tableName = "Rent")
data class Rent(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "rentId") val rentId: Int,
    @Embedded val owner: Owner,
    @ColumnInfo(name = "year") val year: Year,
    @ColumnInfo(name = "month") val month: Month,
    @Embedded val amount: Amount
)
