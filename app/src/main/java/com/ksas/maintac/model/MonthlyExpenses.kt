package com.ksas.maintac.model

data class MonthlyExpenses(
    val bwssbBill: Double = 0.0,
    val bescomBill: Double = 0.0,
    val cctvBill: Double = 0.0,
    val garbageDisposal: Double = 0.0,
    val housekeeping: Double = 0.0,
    val month: Double = 0.0,
    val phenyl: Double = 0.0,
    val year: String = "",
    val userId: String = ""
)

