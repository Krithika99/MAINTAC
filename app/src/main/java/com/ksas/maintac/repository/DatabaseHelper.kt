package com.ksas.maintac.repository

import com.ksas.maintac.model.Rent

interface DatabaseHelper {

    suspend fun insertRentDetails(rent: Rent)

    suspend fun getRentDetails(): List<Rent>
}