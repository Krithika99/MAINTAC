package com.ksas.maintac.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ksas.maintac.viewmodel.IncomeViewModel

@Composable
fun TestScreen() {
    val incomeVM: IncomeViewModel = viewModel()
    incomeVM.getMonthlyIncome("2023", 8)
}
