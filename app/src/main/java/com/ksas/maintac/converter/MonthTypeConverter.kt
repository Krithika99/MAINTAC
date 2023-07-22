package com.ksas.maintac.converter

import androidx.room.TypeConverter
import java.time.Month

class MonthTypeConverter {
    @TypeConverter
    fun toMonth(value: Int): Month? {
        return value?.let {
            Month.of(it)
        }
    }

    @TypeConverter
    fun fromMonth(month: Month?): Int? {
        return month?.value
    }
}