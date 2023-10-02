package com.ksas.maintac.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ksas.maintac.model.Flat
import com.ksas.maintac.response.Response
import com.ksas.maintac.ui.theme.MAINTACTheme
import com.ksas.maintac.utils.Utils
import com.ksas.maintac.viewmodel.IncomeViewModel
import com.ksas.maintac.viewmodel.OwnerViewModel
import java.util.*

@Composable
fun IncomeScreen(
    paddingValues: PaddingValues,
    ownerViewModel: OwnerViewModel = viewModel(),
    incomeViewModel: IncomeViewModel = viewModel()
) {
    val ownerList: MutableList<Flat> = mutableListOf()
    ownerViewModel.fetchOwnerDetails()
    val ownerListLivedata by ownerViewModel.flatsLivedata.observeAsState(initial = emptyList())
    ownerList.addAll(ownerListLivedata)
    Log.d("income", ownerList.toString())

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
                .padding(top = 7.dp, bottom = paddingValues.calculateBottomPadding()),
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
                        var mIncome by remember { mutableStateOf(TextFieldValue()) }
                        var mExpanded by rememberSaveable { mutableStateOf(false) }
                        var mSelectedMode by rememberSaveable { mutableStateOf(Utils.mode) }

                        val paymentMode = listOf(Utils.cash, Utils.bank)

                        Text(text = it.ownerName, modifier = Modifier.weight(2f))
                        TextField(
                            value = mIncome,
                            onValueChange = { mIncome = it },
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
                                        incomeViewModel.ownerIncomeMap[it.ownerName] =
                                            listOf(mIncome.text, mSelectedMode)
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
                    ShowIncomeSubmitButton(incomeViewModel, yearState.text, monthState.text)
                }
            }
        }
    }
}

@Composable
fun ShowIncomeSubmitButton(incomeViewModel: IncomeViewModel, year: String, month: String) {
    val context = LocalContext.current
    val incomeResponseLivedata = incomeViewModel.responseLivedata.value
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 10.dp, top = 10.dp),
    ) {
        Button(
            onClick = {
                incomeViewModel.saveMonthlyIncomeMap(year, month)

                when (incomeResponseLivedata) {
                    is Response.Success -> {
                        println("Success response received")
                        Toast.makeText(
                            context,
                            "Added successfully!!!",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is Response.Failure -> {
                        println("Failure response received")
                        Toast.makeText(
                            context,
                            Utils.somethingWentWrong,
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    is Response.Loading -> {

                    }

                    else -> {

                    }
                }

            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFACD38))
        ) {
            Text(text = "Submit", fontFamily = Utils.customFont)
        }

    }
    TestScreen()
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
        //IncomeScreen()
    }
}