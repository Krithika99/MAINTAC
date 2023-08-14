package com.ksas.maintac.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ksas.maintac.R
import com.ksas.maintac.home_route
import com.ksas.maintac.signin_route
import com.ksas.maintac.utils.MonthDropDown
import com.ksas.maintac.utils.Utils
import com.ksas.maintac.utils.YearDropDown
import com.ksas.maintac.viewmodel.FirebaseAuthenticationViewModel
import java.util.*

@Composable
fun HomeScreen(navController: NavHostController) {
    val firebaseViewModel: FirebaseAuthenticationViewModel = viewModel()
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
                .padding(it)
        ) {
            YearDropDown()
            MonthDropDown()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Button(
                    onClick = {

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

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFACD38))
                ) {
                    Text(text = "Expense", fontFamily = Utils.customFont)
                }

            }
        }
    }
}




