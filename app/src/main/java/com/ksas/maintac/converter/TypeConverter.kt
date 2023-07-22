package com.ksas.maintac.converter

import java.time.Year
import androidx.room.TypeConverter


class YearTypeConverter {

    @TypeConverter
    fun toYear(value: Int): Year? {
        return value.let {
            Year.of(it)
        }
    }

    @TypeConverter
    fun fromYear(year: Year?): Int? {
        return year?.value
    }

}