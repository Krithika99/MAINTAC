package com.ksas.maintac.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.ksas.maintac.home_route
import com.ksas.maintac.response.Response
import com.ksas.maintac.signin_route
import com.ksas.maintac.signup_route
import com.ksas.maintac.utils.Utils
import com.ksas.maintac.viewmodel.FirebaseAuthenticationViewModel
import kotlinx.coroutines.launch

@Composable
fun SignInScreen(
    navController: NavHostController,
    firebaseViewModel: FirebaseAuthenticationViewModel,
    onLoginError: (String, String) -> Unit
) {
    val scope = rememberCoroutineScope()
    Column(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .height(200.dp)
                .clip(RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp))
                .background(color = Color(0XFFFACD38))
        ) {
            Text(
                "Welcome,\nSign in here",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, top = 50.dp),
                fontSize = 28.sp,
                fontFamily = Utils.customFont
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var email by remember {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email", fontFamily = Utils.customFont) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "")
                }
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password", fontFamily = Utils.customFont) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                visualTransformation = PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "")
                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick =
                    {
                        if (email.isNotEmpty() && password.isNotEmpty()) {
                            scope.launch {
                                firebaseViewModel.signInUser(email, password).collect {
                                    when (it) {
                                        is Response.Success -> {
                                            navController.navigate(home_route) {
                                                popUpTo(navController.graph.id) {
                                                    inclusive = true
                                                }
                                            }
                                        }
                                        is Response.Failure -> onLoginError(
                                            Utils.somethingWentWrong,
                                            Utils.signin
                                        )
                                        is Response.Loading -> {

                                        }
                                    }
                                }
//                                if (firebaseViewModel.signIn(email, password)) {
//                                    navController.navigate(home_route) {
//                                        popUpTo(signin_route) {
//                                            inclusive = true
//                                        }
//                                    }
//                                } else {
//                                    onLoginError(Utils.somethingWentWrong, Utils.signin)
//                                }
                            }
                        }

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFACD38))

                ) {
                    Text(
                        text = "Sign In",
                        color = MaterialTheme.colors.onSurface,
                        fontFamily = Utils.customFont,
                        fontSize = 18.sp
                    )
                }
                Row {
                    Text(
                        text = "Create account",
                        modifier = Modifier,
                        fontFamily = Utils.customFont,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Sign up",
                        modifier = Modifier
                            .clickable {
                                navController.navigate(signup_route) {
                                    launchSingleTop = true
                                    popUpTo(signup_route) {
                                        inclusive = true
                                    }
                                }
                            }
                            .padding(start = 5.dp),
                        style = TextStyle(textDecoration = TextDecoration.Underline),
                        fontFamily = Utils.customFont,
                        fontSize = 14.sp
                    )
                }


            }
        }
    }
}