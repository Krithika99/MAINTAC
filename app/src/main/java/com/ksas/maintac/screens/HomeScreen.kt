package com.ksas.maintac.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.google.firebase.firestore.DocumentSnapshot
import com.ksas.maintac.R
import com.ksas.maintac.home_route
import com.ksas.maintac.response.Response
import com.ksas.maintac.signin_route
import com.ksas.maintac.utils.MonthDropDown
import com.ksas.maintac.utils.Utils
import com.ksas.maintac.utils.YearDropDown
import com.ksas.maintac.viewmodel.FirebaseAuthenticationViewModel
import com.ksas.maintac.viewmodel.HomeViewModel


@SuppressLint("UnrememberedMutableState")
@Composable
fun HomeScreen(navController: NavHostController) {
    val firebaseViewModel: FirebaseAuthenticationViewModel = viewModel()
    val homeViewModel: HomeViewModel = viewModel()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontFamily = Utils.customFont
                    )
                },
                actions = {
                    IconButton(onClick = {
                        firebaseViewModel.signOut()
                        navController.navigate(signin_route) {
                            launchSingleTop = true
                            popUpTo(home_route) {
                                inclusive = true
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Sign out")
                    }
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(it.calculateBottomPadding())
        ) {
            val year = YearDropDown()
            val month = MonthDropDown()
            val incomeState by homeViewModel.incomeState
            val expenseState by homeViewModel.expenseState
            var curUserId = remember {
                mutableStateOf("")
            }
            var flag: MutableState<Boolean> = remember {
                mutableStateOf(false)
            }
            var editFlag: MutableState<Boolean> = remember {
                mutableStateOf(false)
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Button(
                    onClick = {
                        homeViewModel.showIncome(year, month)
                        flag.value = true
                        homeViewModel.response.observeForever { response ->
                            when (response) {
                                is Response.Success -> {
                                    curUserId.value = response.data
                                    Log.d("TAGG", curUserId.value)

                                }

                                is Response.Failure -> {
                                    Log.e("TAG", response.message)
                                }

                                else -> {

                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFACD38))
                ) {
                    Text(text = "Income", fontFamily = Utils.customFont)
                }

                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        homeViewModel.showExpense(year, month)
                        flag.value = false
                        homeViewModel.response.observeForever { response ->
                            when (response) {
                                is Response.Success -> {
                                    curUserId.value = response.data
                                    Log.d("TAGG", curUserId.value)
                                }

                                is Response.Failure -> {
                                    Log.e("TAG", response.message)
                                }

                                else -> {

                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFACD38))
                ) {
                    Text(text = "Expense", fontFamily = Utils.customFont)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        editFlag.value = true
                        if (flag.value) {
                            homeViewModel.editDetails(incomeState, year, month)
                        } else {
                            homeViewModel.editDetails(expenseState, year, month)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFACD38))
                ) {
                    Text(text = "Edit", fontFamily = Utils.customFont)
                }

            }
            if (flag.value) {
                ShowIncomeExpenseDetails(
                    documentSnapshot = incomeState,
                    curUserId = curUserId.value
                )
            } else {
                ShowIncomeExpenseDetails(
                    documentSnapshot = expenseState,
                    curUserId = curUserId.value
                )
            }
        }
    }
}

@Composable
fun ShowIncomeExpenseDetails(documentSnapshot: DocumentSnapshot?, curUserId: String) {

    val data = documentSnapshot?.data

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (data != null) {
            for ((key, value) in data) {
                if (key == "userid") {
                    continue
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "$key", fontFamily = Utils.customFont, fontSize = 20.sp)
                    Text(text = "$value", fontFamily = Utils.customFont, fontSize = 20.sp)
                }
            }
        } else {
            Text(text = "No data available")
        }
    }
}






