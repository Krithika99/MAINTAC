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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ksas.maintac.model.MonthlyExpenseNames
import com.ksas.maintac.utils.Utils
import com.ksas.maintac.viewmodel.ExpenseViewModel
import java.util.*

@Composable
fun ExpenseScreen() {
    val expenseViewmodel: ExpenseViewModel = viewModel()
    val expenseList: MutableList<MonthlyExpenseNames> = mutableListOf()
    expenseList.addAll(
        Utils.expenseNames
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
            placeholder = { Text(text = "${Calendar.getInstance().get(Calendar.MONTH) + 1}") },
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
                    var mSelectedMode by rememberSaveable { mutableStateOf(Utils.mode) }

                    val paymentMode = listOf(Utils.cash, Utils.bank)

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
                                    expenseViewmodel.billMap[it.expense] =
                                        listOf(mExpense.text, mSelectedMode)
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
                ShowSubmitButton(expenseViewmodel, yearState.text, monthState.text)
            }
        }
    }
}

@Composable
fun ShowSubmitButton(expenseViewmodel: ExpenseViewModel, year: String, month: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp, top = 10.dp),
    ) {
        Button(
            onClick = {
                expenseViewmodel.saveMonthlyBillMap(year, month)
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFACD38))
        ) {
            Text(text = "Submit", fontFamily = Utils.customFont)
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

@Preview
@Composable
fun ShowPreview() {
    ExpenseScreen()
}