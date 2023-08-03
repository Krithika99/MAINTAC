package com.ksas.maintac.utils

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import com.ksas.maintac.R
import com.ksas.maintac.owner_route
import com.ksas.maintac.signin_route
import com.ksas.maintac.signup_route
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        val customFont = FontFamily(
            Font(R.font.happymonkeyregular)
        )

        private val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDate: String = sdf.format(Date())
        const val somethingWentWrong = "Something Went Wrong!"
        const val passwordMismatch = "Password mismatch!"
        const val warning = "Warning!"
        const val signup = "signup"
        const val signin = "signin"
        const val ownerPage = "ownerPage"
        const val email = "Email"
        const val password = "Password"
        const val confirmPassword = "Confirm password"
        const val electricityBill = "Bescom bill ECS"
        const val waterBill = "BWSSB Bill ECS"
        const val houseKeeping = "HouseKeeping"
        const val garbageDisposal = "Garbage disposal"
        const val phenyl = "Phenyl"
        const val CCTV = "CCTV"
        const val civilWork = "Civil work"
        const val electricalWork = "Electrical work"

        fun generateListOfYears(startYear: Int, endYear: Int): List<Int> {
            return (startYear..endYear).toList()
        }
    }
}

@Composable
fun WarningDialog(
    warningMessage: String,
    screenType: String,
    dialogState: MutableState<Boolean>,
    navController: NavHostController
) {

    val dialog = @Composable {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AlertDialog(
                onDismissRequest = { dialogState.value = false },
                title = { Text(text = Utils.warning) },
                text = {
                    Text(
                        text = warningMessage
                    )
                }, buttons = {
                    Button(
                        onClick =
                        {
                            dialogState.value = false
                            when (screenType) {
                                "signup" -> {
                                    navController.navigate(signup_route) {
                                        launchSingleTop = true
                                        popUpTo(signup_route) {
                                            inclusive = true
                                        }
                                    }
                                }
                                "signin" -> {
                                    navController.navigate(signin_route) {
                                        launchSingleTop = true
                                        popUpTo(signin_route) {
                                            inclusive = true
                                        }
                                    }
                                }
                                "ownerPage" -> {
                                    navController.navigate(owner_route) {
                                        launchSingleTop = true
                                        popUpTo(owner_route) {
                                            inclusive = true
                                        }
                                    }
                                }
                            }

                        },
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    ) { Text("OK") }
                }
            )
        }
    }

    if (dialogState.value) {
        dialog()
    }
}