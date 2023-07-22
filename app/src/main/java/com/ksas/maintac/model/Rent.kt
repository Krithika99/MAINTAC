package com.ksas.maintac.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Month
import java.time.Year

@Entity(tableName = "Rent")
data class Rent(
    @PrimaryKey @ColumnInfo(name = "rentId") val rentId: Int,
    @Embedded val owner: Owner,
    @ColumnInfo(name = "year") val year: String,
    @ColumnInfo(name = "month") val month: String,
    @Embedded val amount: Amount

)
