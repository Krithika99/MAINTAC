package com.ksas.maintac.screens

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
import com.ksas.maintac.response.Response
import com.ksas.maintac.signin_route
import com.ksas.maintac.utils.Utils
import com.ksas.maintac.viewmodel.FirebaseAuthenticationViewModel
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    navController: NavHostController,
    firebaseViewModel: FirebaseAuthenticationViewModel,
    onPasswordMismatch: (String, String) -> Unit
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
                "Welcome,\nSign up here",
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

            var confirmPassword by remember {
                mutableStateOf("")
            }

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = Utils.email, fontFamily = Utils.customFont) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                leadingIcon = {
                    Icon(Icons.Default.Email, contentDescription = "")
                },
                colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Black)
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = Utils.password, fontFamily = Utils.customFont) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "")
                },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Black)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(text = Utils.confirmPassword, fontFamily = Utils.customFont) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                leadingIcon = {
                    Icon(Icons.Default.Lock, contentDescription = "")
                },
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.textFieldColors(focusedIndicatorColor = Color.Black)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = {
                        if (email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                            if (password == confirmPassword) {
                                scope.launch {

                                    firebaseViewModel.signUpUser(email, password).collect {
                                        when (it) {
                                            is Response.Success -> {
                                                navController.navigate(signin_route) {
                                                    popUpTo(navController.graph.id) {
                                                        inclusive = true
                                                    }
                                                }
                                            }

                                            is Response.Failure -> {
                                                onPasswordMismatch(
                                                    Utils.somethingWentWrong,
                                                    Utils.signup
                                                )
                                            }

                                            is Response.Loading -> {}
                                        }
                                    }
                                }

                            } else {
                                onPasswordMismatch(Utils.passwordMismatch, Utils.signup)
                            }

                        }

                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFACD38))

                ) {
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colors.onSurface,
                        fontFamily = Utils.customFont,
                        fontSize = 18.sp
                    )
                }
                Row {
                    Text(
                        text = "Already signed up?",
                        modifier = Modifier,
                        fontFamily = Utils.customFont,
                        fontSize = 14.sp
                    )

                    Text(
                        text = "Login",
                        modifier = Modifier
                            .clickable {
                                navController.navigate(signin_route) {
                                    popUpTo(signin_route) {
                                        inclusive = true
                                    }
                                    launchSingleTop = true
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

