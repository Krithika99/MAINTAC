package com.ksas.maintac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ksas.maintac.database.DatabaseBuilder
import com.ksas.maintac.model.Amount
import com.ksas.maintac.model.Owner
import com.ksas.maintac.model.Rent
import com.ksas.maintac.repository.DatabaseHelperImpl
import com.ksas.maintac.ui.theme.MAINTACTheme
import com.ksas.maintac.viewmodel.RentViewModel
import java.time.Month
import java.time.Year

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
                    Home()
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MAINTACTheme {
        Home()
    }
}