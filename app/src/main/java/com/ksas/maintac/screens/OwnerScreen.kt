package com.ksas.maintac.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ksas.maintac.utils.Utils
import com.ksas.maintac.viewmodel.AddViewModel

@Composable
fun OwnerScreen(onError: (String, String) -> Unit) {
    var mOwner by remember {
        mutableStateOf("")
    }
    var mFlat by remember {
        mutableStateOf("")
    }
    var mTenant by remember {
        mutableStateOf("")
    }
    val addViewModel: AddViewModel = viewModel()
    val flats by addViewModel.flatsLivedata.observeAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Add owner details",
            fontFamily = Utils.customFont,
            fontSize = 20.sp,
            color = MaterialTheme.colors.onPrimary
        )

        TextField(
            value = mOwner,
            onValueChange = { mOwner = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Black),
            label = {
                Text(
                    text = "Owner name",
                    fontFamily = Utils.customFont,
                    color = MaterialTheme.colors.onPrimary
                )
            },
            placeholder = {
                Text(
                    text = "XXX",
                    fontFamily = Utils.customFont,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        )
        TextField(
            value = mFlat,
            onValueChange = { mFlat = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Black),
            label = {
                Text(
                    text = "Flat no",
                    fontFamily = Utils.customFont,
                    color = MaterialTheme.colors.onPrimary
                )
            },
            placeholder = {
                Text(
                    text = "000",
                    fontFamily = Utils.customFont,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        )
        TextField(
            value = mTenant,
            onValueChange = { mTenant = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Black),
            label = {
                Text(
                    text = "Tenant name",
                    fontFamily = Utils.customFont,
                    color = MaterialTheme.colors.onPrimary
                )
            },
            placeholder = {
                Text(
                    text = "YYY",
                    fontFamily = Utils.customFont,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        )
        Button(
            onClick = {
                if (mOwner != "" && mFlat != "" && mTenant != "") {
                    addViewModel.storeOwnerDetails(mOwner, mFlat, mTenant)
                } else {
                    onError("Empty fields!!", Utils.ownerPage)
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFACD38))
        ) {
            Text(
                text = "Create/Update",
                fontFamily = Utils.customFont,
                color = MaterialTheme.colors.onPrimary
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedButton(
            onClick = { addViewModel.fetchOwnerDetails() },
            modifier = Modifier
        ) {
            Text(
                text = "Owner details",
                fontFamily = Utils.customFont,
                fontSize = 20.sp,
                color = MaterialTheme.colors.onPrimary
            )
        }
        LazyColumn {
            items(flats) { flat ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    shape = RectangleShape,
                    backgroundColor = Color(color = 0XFFBCC2CC)
                ) {
                    Column(modifier = Modifier.padding(20.dp)) {
                        Text(
                            text = "Flat No:   ${flat.flatNo}",
                            fontFamily = Utils.customFont,
                            color = MaterialTheme.colors.onPrimary
                        )
                        Text(
                            text = "Owner Name:   ${flat.ownerName}",
                            fontFamily = Utils.customFont,
                            color = MaterialTheme.colors.onPrimary
                        )
                        Text(
                            text = "Tenant:   ${flat.tenant}",
                            fontFamily = Utils.customFont,
                            color = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
    }
}

