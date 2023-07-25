package com.ksas.maintac.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ksas.maintac.utils.Utils

@Composable
fun SignUpScreen() {
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
                label = { Text(text = "Email", fontFamily = Utils.customFont) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password", fontFamily = Utils.customFont) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text(text = "Confirm Password", fontFamily = Utils.customFont) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Button(
                    onClick = { /*TODO*/ }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFFFACD38))

                ) {
                    Text(
                        text = "Sign Up",
                        color = MaterialTheme.colors.onSurface,
                        fontFamily = Utils.customFont
                    )
                }
            }
        }
    }
}