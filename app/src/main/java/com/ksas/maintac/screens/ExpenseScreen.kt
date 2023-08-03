package com.ksas.maintac.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ksas.maintac.model.MonthlyExpenseNames
import com.ksas.maintac.utils.Utils
import java.util.*

@Composable
fun ExpenseScreen() {
    val expense1 = MonthlyExpenseNames(Utils.electricityBill)
    val expense2 = MonthlyExpenseNames(Utils.waterBill)
    val expense3 = MonthlyExpenseNames(Utils.houseKeeping)
    val expense4 = MonthlyExpenseNames(Utils.garbageDisposal)
    val expense5 = MonthlyExpenseNames(Utils.phenyl)
    val expense6 = MonthlyExpenseNames(Utils.CCTV)
    val expense7 = MonthlyExpenseNames(Utils.civilWork)
    val expense8 = MonthlyExpenseNames(Utils.electricalWork)

    val expenseList: MutableList<MonthlyExpenseNames> = mutableListOf()

    expenseList.addAll(
        listOf(
            expense1,
            expense2,
            expense3,
            expense4,
            expense5,
            expense6,
            expense7,
            expense8
        )
    )

    var yearState by remember { mutableStateOf(TextFieldValue()) }
    var monthState by remember {
        mutableStateOf(TextFieldValue())
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = yearState,
            onValueChange = { yearState = it },
            label = { Text(text = "Please enter the year") },
            placeholder = { Text(text = "${Calendar.getInstance().get(Calendar.YEAR)}") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )

        OutlinedTextField(
            value = monthState,
            onValueChange = { monthState = it },
            label = { Text(text = "Please enter the month") },
            placeholder = { Text(text = "${Calendar.getInstance().get(Calendar.MONTH)}") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalArrangement = Arrangement.Center,
            userScrollEnabled = true
        ) {

            item {
                ShowMonthlyExpenseHeading()
            }
            items(expenseList) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var mExpense by remember { mutableStateOf(TextFieldValue()) }
                    var mExpanded by rememberSaveable { mutableStateOf(false) }
                    var mSelectedMode by rememberSaveable { mutableStateOf("Mode") }

                    val paymentMode = listOf("Cash", "Bank")

                    Text(text = it.expense, modifier = Modifier.weight(2f))
                    TextField(
                        value = mExpense,
                        onValueChange = { mExpense = it },
                        modifier = Modifier.weight(1f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Box(modifier = Modifier
                        .weight(1f)
                        .clickable { mExpanded = !mExpanded }) {
                        Row(
                            modifier = Modifier,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = mSelectedMode, modifier = Modifier.padding(16.dp))
                            Icon(
                                imageVector = Icons.Default.ArrowDropDown,
                                contentDescription = "Drop Down Icon"
                            )
                        }

                        DropdownMenu(
                            expanded = mExpanded,
                            onDismissRequest = { mExpanded = false },
                            modifier = Modifier,
                        ) {
                            paymentMode.forEach { paymentMode ->
                                DropdownMenuItem(onClick = {
                                    mSelectedMode = paymentMode
                                    mExpanded = false
                                }) {
                                    Text(
                                        text = paymentMode,
                                        fontFamily = Utils.customFont
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item {
                ShowSubmitButton()
            }
        }
    }
}


@Composable
fun ShowMonthlyExpenseHeading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Monthly Expense", fontSize = 20.sp, fontFamily = Utils.customFont)
    }
}