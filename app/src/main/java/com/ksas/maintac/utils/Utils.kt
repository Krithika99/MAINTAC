package com.ksas.maintac.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ksas.maintac.R
import com.ksas.maintac.model.MonthlyExpenseNames
import com.ksas.maintac.model.MonthlyExpenses
import com.ksas.maintac.owner_route
import com.ksas.maintac.signin_route
import com.ksas.maintac.signup_route
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        val customFont = FontFamily(
            Font(R.font.happymonkeyregular)
        )

        private val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate: String = sdf.format(Date())
        const val somethingWentWrong = "Something Went Wrong!"
        const val passwordMismatch = "Password mismatch!"
        const val emptyFields = "Empty fields!!"
        const val warning = "Warning!"
        const val signup = "signup"
        const val signin = "signin"
        const val ownerPage = "ownerPage"
        const val email = "Email"
        const val password = "Password"
        const val confirmPassword = "Confirm password"
        const val electricityBill = "Bescom bill ECS"
        const val waterBill = "BWSSB Bill ECS"
        const val houseKeeping = "HouseKeeping"
        const val garbageDisposal = "Garbage disposal"
        const val phenyl = "Phenyl"
        const val CCTV = "CCTV"
        const val cash = "Cash"
        const val bank = "Bank"
        const val mode = "Mode"
        const val civilWork = "Civil work"
        const val electricalWork = "Electrical work"
        const val MonthlyExpense = "Monthly Expense"
        const val MonthlyIncome = "Monthly Income"

        fun generateListOfYears(startYear: Int, endYear: Int): List<Int> {
            return (startYear..endYear).toList()
        }

        fun generateListOfMonths(startMonth: Int, endMonth: Int): List<Int> {
            return (startMonth..endMonth).toList()
        }

        val expenseNames = listOf(
            MonthlyExpenseNames(electricityBill),
            MonthlyExpenseNames(waterBill), MonthlyExpenseNames(houseKeeping),
            MonthlyExpenseNames(garbageDisposal), MonthlyExpenseNames(phenyl),
            MonthlyExpenseNames(CCTV)
        )
    }
}

@Composable
fun WarningDialog(
    warningMessage: String,
    screenType: String,
    dialogState: MutableState<Boolean>,
    navController: NavHostController
) {

    val dialog = @Composable {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AlertDialog(
                onDismissRequest = { dialogState.value = false },
                title = { Text(text = Utils.warning) },
                text = {
                    Text(
                        text = warningMessage
                    )
                }, buttons = {
                    Button(
                        onClick =
                        {
                            dialogState.value = false
                            when (screenType) {
                                "signup" -> {
                                    navController.navigate(signup_route) {
                                        launchSingleTop = true
                                        popUpTo(signup_route) {
                                            inclusive = true
                                        }
                                    }
                                }
                                "signin" -> {
                                    navController.navigate(signin_route) {
                                        launchSingleTop = true
                                        popUpTo(signin_route) {
                                            inclusive = true
                                        }
                                    }
                                }
                                "ownerPage" -> {
                                    navController.navigate(owner_route) {
                                        launchSingleTop = true
                                        popUpTo(owner_route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }

                        },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    ) { Text("OK") }
                }
            )
        }
    }

    if (dialogState.value) {
        dialog()
    }
}

@Composable
fun YearDropDown() {
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }

    // Create a list of years
    val years = Utils.generateListOfYears(
        2000,
        2050
    )
    // Create a string value to store the selected year
    var mSelectedYear by remember { mutableStateOf(currentYear.toString()) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {

        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            value = mSelectedYear,
            onValueChange = { mSelectedYear = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("Select the year", fontFamily = Utils.customFont, fontSize = 16.sp) },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        // Create a drop-down menu with list of cities,
        // when clicked, set the Text Field text as the city selected
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier,
        ) {
            years.forEach { year ->
                DropdownMenuItem(onClick = {
                    mSelectedYear = year.toString()
                    mExpanded = false
                }) {
                    Text(text = year.toString(), fontFamily = Utils.customFont)
                }
            }
        }
    }
}

@Composable
fun MonthDropDown() {
    val currentMonth = Calendar.getInstance().get(Calendar.MONTH)
    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var mExpanded by remember { mutableStateOf(false) }

    // Create a list of years
    val months = Utils.generateListOfMonths(
        1,
        12
    )
    // Create a string value to store the selected year
    var mSelectedMonth by remember { mutableStateOf(currentMonth.toString()) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(20.dp)) {

        // Create an Outlined Text Field
        // with icon and not expanded
        OutlinedTextField(
            value = mSelectedMonth,
            onValueChange = { mSelectedMonth = it },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text("Select the Month", fontFamily = Utils.customFont, fontSize = 16.sp) },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { mExpanded = !mExpanded })
            }
        )

        // Create a drop-down menu with list of cities,
        // when clicked, set the Text Field text as the city selected
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier,
        ) {
            months.forEach { month ->
                DropdownMenuItem(onClick = {
                    mSelectedMonth = month.toString()
                    mExpanded = false
                }) {
                    Text(text = month.toString(), fontFamily = Utils.customFont)
                }
            }
        }
    }
}