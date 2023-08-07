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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ksas.maintac.R
import com.ksas.maintac.home_route
import com.ksas.maintac.signin_route
import com.ksas.maintac.utils.Utils
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
        }
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
        1980,
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
