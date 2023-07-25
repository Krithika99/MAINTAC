package com.ksas.maintac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ksas.maintac.database.DatabaseBuilder
import com.ksas.maintac.model.Amount
import com.ksas.maintac.model.Owner
import com.ksas.maintac.model.Rent
import com.ksas.maintac.repository.DatabaseHelperImpl
import com.ksas.maintac.screens.SignUpScreen
import com.ksas.maintac.ui.theme.MAINTACTheme
import com.ksas.maintac.utils.Utils
import com.ksas.maintac.viewmodel.RentViewModel
import java.text.SimpleDateFormat
import java.time.Month
import java.time.Year
import java.util.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MAINTACTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //AppNavigator()
                    SignUpScreen()
                }
            }
        }
    }
}

@Composable
fun Home() {
    val viewModel: RentViewModel = viewModel()
    val database = DatabaseBuilder.getInstance(LocalContext.current)
    val databaseHelperImpl = DatabaseHelperImpl(database = database.rentDao())
    viewModel.setDataBasHelper(databaseHelperImpl)
    viewModel.getRentDetails()
    val owner = Owner("105", "Ganesh", "Guru")
    val amount = Amount(1000.00, 0.00)
    val rent = Rent(4, owner, Year.of(2023), Month.of(4), amount)
    val rent2 =
        Rent(3, Owner("102", "Pavani", "Jia"), Year.of(2023), Month.of(6), Amount(1200.00, 0.00))
    viewModel.insertRentDetails(rent)

    val rentList by viewModel.rent.asFlow().collectAsState(initial = emptyList())

    LazyColumn {
        items(rentList) {
            Text(text = it.rentId.toString())
            Text(text = it.month.name)
            Text(text = it.year.value.toString())
            Text(text = it.owner.ownerName)
            Text(text = it.amount.cash.toString())

        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(10.dp)
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MAINTACTheme {

    }
}