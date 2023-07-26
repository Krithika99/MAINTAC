package com.ksas.maintac

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ksas.maintac.screens.SignInScreen
import com.ksas.maintac.screens.SignUpScreen
import com.ksas.maintac.utils.Utils
import com.ksas.maintac.utils.WarningDialog
import com.ksas.maintac.viewmodel.FirebaseAuthenticationViewModel

const val home_route = "home_page"
const val add_route = "add_page"
const val owner_route = "owner_page"
const val signup_route = "signup_page"
const val signin_route = "signin_page"
const val warning_route = "warning_page"
const val warningMsg = "warningMsg"
const val screenType = "screenType"

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
    val firebaseViewModel: FirebaseAuthenticationViewModel = viewModel()

    NavHost(navController = navController, startDestination = signin_route) {
        composable(signin_route) {
            SignInScreen(navController, firebaseViewModel) { message, screenType ->
                navController.navigate("$warning_route/${message}/${screenType}") {
                    launchSingleTop = true
                    popUpTo(warning_route) {
                        inclusive = true
                    }
                }
            }
        }

        composable(home_route) {
            ScaffoldWithBottomNavigation(navController = navController) {
                HomeScreen(navController)
            }
        }

        composable(signup_route) {
            SignUpScreen(navController = navController, firebaseViewModel) { message, screenType ->
                navController.navigate("$warning_route/${message}/${screenType}") {
                    launchSingleTop = true
                    popUpTo(warning_route) {
                        inclusive = true
                    }
                }

            }
        }
        composable("$warning_route/{$warningMsg}/{$screenType}",
            arguments = listOf(
                navArgument(warningMsg) {
                    type = NavType.StringType
                },
                navArgument(screenType) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val dialogState = remember { mutableStateOf(false) }
            val warningMsg = backStackEntry.arguments?.getString(warningMsg)
            val screenType = backStackEntry.arguments?.getString(screenType)
            if (warningMsg != null && screenType != null) {
                dialogState.value = true
                WarningDialog(
                    warningMessage = warningMsg,
                    screenType = screenType,
                    dialogState,
                    navController
                )
            }
        }
    }
}

@Composable
fun ScaffoldWithBottomNavigation(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(bottomBar = {
        BottomNavigation(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colors.background),
            contentColor = MaterialTheme.colors.onSurface
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            BottomNavigationItem(
                selected = currentRoute == home_route,
                onClick = {
                    navController.navigate(home_route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(Icons.Default.Home, contentDescription = null, modifier = Modifier)
                },
                label = {
                    Text(text = "Home", modifier = Modifier, fontFamily = Utils.customFont)
                }
            )

            BottomNavigationItem(
                selected = currentRoute == add_route,
                onClick = {
                    navController.navigate(add_route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier)
                },
                label = {
                    Text(text = "Add", modifier = Modifier, fontFamily = Utils.customFont)
                }
            )

            BottomNavigationItem(
                selected = currentRoute == owner_route,
                onClick = {
                    navController.navigate(owner_route) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier)
                },
                label = {
                    Text(text = "Owners", modifier = Modifier, fontFamily = Utils.customFont)
                }
            )
        }
    }) { innerPadding ->
        content(innerPadding)

    }
}


