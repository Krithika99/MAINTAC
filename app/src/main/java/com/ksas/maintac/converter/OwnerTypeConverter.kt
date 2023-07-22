package com.ksas.maintac.converter

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ksas.maintac.model.Owner

class OwnerTypeConverter {
    private val gson = Gson()

    @TypeConverter
    fun ownerToString(owner: Owner): String? {
        return gson.toJson(owner)
    }

    @TypeConverter
    fun stringToOwner(ownerString: String): Owner? {
        val ownerType = object : TypeToken<Owner>() {}.type
        return gson.fromJson(ownerString, ownerType)
    }
}