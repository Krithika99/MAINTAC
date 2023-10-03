package com.ksas.maintac.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.firestore.DocumentSnapshot
import com.ksas.maintac.viewmodel.HomeViewModel

@Composable
fun EditIncome(year: String, month: String, flag: Boolean) {


    var remYear by remember {
        mutableStateOf(year)
    }

    var remMonth by remember {
        mutableStateOf(month)
    }

    var homeViewModel: HomeViewModel = viewModel()
    homeViewModel.showIncome(remYear, remMonth)
    val incomeState by homeViewModel.incomeState
    val expenseState by homeViewModel.expenseState
    Column {
        Column {
            OutlinedTextField(value = remYear, onValueChange = {
                remYear = it
            })

            OutlinedTextField(value = remMonth, onValueChange = {
                remMonth = it
            })

            if (flag) {
                ShowTextField(documentSnapshot = incomeState)
            } else {
                ShowTextField(documentSnapshot = expenseState)
            }
        }
    }
}

@Composable
fun ShowTextField(documentSnapshot: DocumentSnapshot?) {
    var data = documentSnapshot?.data
    var map by remember {
        mutableStateOf(data)
    }
    Log.d("Tagg", map.toString())
    Log.d("Taggg", documentSnapshot?.data.toString())
    if (map != null) {
        for ((key, value) in map!!) {
            var remKey by remember {
                mutableStateOf("")
            }
            remKey = key

            Row {
                TextField(value = remKey, onValueChange = { remKey = it })
            }

        }
    }
}
