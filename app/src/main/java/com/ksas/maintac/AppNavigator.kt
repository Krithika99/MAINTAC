package com.ksas.maintac

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ksas.maintac.utils.Utils

const val home_route = "home_page"
const val add_route = "add_page"
const val owner_route = "owner_page"

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppNavigator() {
    val navController = rememberNavController()
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
    }) {
        NavHost(navController = navController, startDestination = home_route) {
            composable(home_route) {
                HomeScreen(navController)
            }
        }

    }

}

