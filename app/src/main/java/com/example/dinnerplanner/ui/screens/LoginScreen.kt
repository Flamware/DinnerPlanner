package com.example.dinnerplanner.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.dinnerplanner.Fond
import com.example.dinnerplanner.R
import com.example.dinnerplanner.data.local.viewmodel.AuthViewModel

@Composable
fun LoginScreen(authViewModel: AuthViewModel, navController: NavController) {
    val username = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val currentUser by authViewModel.currentUser.observeAsState()
    val registrationState by authViewModel.registrationState.observeAsState()
    val loginState by authViewModel.loginState.observeAsState()

    Fond()
    Image(
        painter = painterResource(id = R.drawable.dinner_planner),
        contentDescription = "Dinner Planner",
        modifier = Modifier.fillMaxWidth()
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp) // Add padding around the Box
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) { // Center the Column
            OutlinedTextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Username") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                keyboardActions = KeyboardActions(onNext = { /* Handle next action */ }),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                )

            )

            Spacer(modifier = Modifier.height(16.dp)) // Add vertical space

            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text("Password") },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { /* Handle done action */ }),
                visualTransformation = PasswordVisualTransformation(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp)) // Add vertical space

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly // Distribute the Buttons evenly
            ) {
                Button(onClick = {
                    // Handle login
                    if (username.value.isNotEmpty() && password.value.isNotEmpty())
                        authViewModel.login(username.value, password.value)
                }) {
                    Text("Log in")
                }

                Button(onClick = {
                    // Handle sign up
                    if (username.value.isNotEmpty() && password.value.isNotEmpty())
                        authViewModel.register(username.value, password.value)
                }) {
                    Text("Sign up")
                }
            }

            // Check loginState here
            when (loginState) {
                is AuthViewModel.Result.Success -> {
                    Text("Login successful")
                    navController.navigate("home")
                }
                is AuthViewModel.Result.Error -> {
                    Text("Login failed")
                }
                null -> {
                    Text("Please log in")
                }
            }

            // Check registrationState here
            when (registrationState) {
                is AuthViewModel.Result.Success -> {
                    Text("Registration successful")
                }
                is AuthViewModel.Result.Error -> {
                    Text("Registration failed")
                }
                null -> {
                }
            }
        }
    }
}
