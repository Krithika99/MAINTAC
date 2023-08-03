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
import com.ksas.maintac.model.MonthlyExpenseNames
import com.ksas.maintac.model.Owner
import com.ksas.maintac.ui.theme.MAINTACTheme
import com.ksas.maintac.utils.Utils
import java.util.*

@Composable
fun IncomeScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
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

            val owner1 = Owner("101", "Shishir", "none")
            val owner2 = Owner("102", "Mahalingappa", "Meena")
            val owner3 = Owner("102", "Mahalingappa", "Meena")
            val owner4 = Owner("102", "Mahalingappa", "Meena")
            val owner5 = Owner("102", "Mahalingappa", "Meena")
            val owner6 = Owner("102", "Mahalingappa", "Meena")
            val owner7 = Owner("102", "Mahalingappa", "Meena")
            val owner8 = Owner("102", "Mahalingappa", "Meena")
            val owner9 = Owner("102", "Mahalingappa", "Meena")

            val expense1 = MonthlyExpenseNames(Utils.electricityBill)
            val expense2 = MonthlyExpenseNames(Utils.waterBill)
            val expense3 = MonthlyExpenseNames(Utils.houseKeeping)
            val expense4 = MonthlyExpenseNames(Utils.garbageDisposal)
            val expense5 = MonthlyExpenseNames(Utils.phenyl)
            val expense6 = MonthlyExpenseNames(Utils.CCTV)

            val ownerList: MutableList<Owner> = mutableListOf()
            val expenseList: MutableList<MonthlyExpenseNames> = mutableListOf()

            ownerList.addAll(
                listOf(
                    owner1,
                    owner2,
                    owner3,
                    owner4,
                    owner5,
                    owner6,
                    owner7,
                    owner8,
                    owner9
                )
            )
            expenseList.addAll(listOf(expense1, expense2, expense3, expense4, expense5, expense6))
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                verticalArrangement = Arrangement.Center,
                userScrollEnabled = true
            )
            {
                item {
                    ShowIncomeHeading()
                }
                items(ownerList) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var ownerName by remember { mutableStateOf(TextFieldValue()) }
                        var mExpanded by rememberSaveable { mutableStateOf(false) }
                        var mSelectedMode by rememberSaveable { mutableStateOf("Mode") }

                        val paymentMode = listOf("Cash", "Bank")

                        Text(text = it.ownerName, modifier = Modifier.weight(2f))
                        TextField(
                            value = ownerName,
                            onValueChange = { ownerName = it },
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
}

@Composable
fun ShowSubmitButton() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp, top = 10.dp),
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFACD38))
        ) {
            Text(text = "Submit", fontFamily = Utils.customFont)
        }

    }
}


@Composable
fun ShowIncomeHeading() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Income", fontFamily = Utils.customFont, fontSize = 20.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MAINTACTheme {
        IncomeScreen()
    }
}