package com.ksas.maintac.repository

import com.ksas.maintac.database.RentDao
import com.ksas.maintac.model.Rent

class DatabaseHelperImpl(private val database: RentDao) : DatabaseHelper {
    override suspend fun insertRentDetails(rent: Rent) {
        database.insertRentDetails(rent)
    }

    override suspend fun getRentDetails(): List<Rent> {
        return database.getRentDetails()
    }

    override suspend fun getRentDetailsByYear(year: String): List<Rent> {
        return database.getRentDetailsByYear(year)
    }


}