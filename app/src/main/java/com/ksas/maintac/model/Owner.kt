package com.ksas.maintac.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Owner")
data class Owner(
    @PrimaryKey @ColumnInfo(name = "flatNo") val flatNo: String,
    @ColumnInfo(name = "ownerName") val ownerName: String,
    @ColumnInfo(name = "tenant") val tenant: String
)
