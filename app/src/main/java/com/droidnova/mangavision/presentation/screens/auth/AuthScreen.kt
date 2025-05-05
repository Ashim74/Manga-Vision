package com.droidnova.mangavision.presentation.screens.auth

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.droidnova.mangavision.domain.data.AuthStatus

@Composable
fun AuthScreen(authViewModel: AuthViewModel, onLoginSuccess: () -> Unit) {
    val state = authViewModel.state
    val onEvent = authViewModel::onEvent
    LaunchedEffect(state.status) {
        if (state.status == AuthStatus.Authenticated) onLoginSuccess()
    }
    AuthScreenContent(state,onEvent)

}

@Preview(showBackground = true)
@Composable
fun show(){
    AuthScreenContent(
        AuthState(),
        onEvent = {}
    )
}

@Composable
fun AuthScreenContent(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome Back",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.ExtraBold
            )
        )

        Text(
            text = "Please enter your credentials to sign in",
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            ),
            modifier = Modifier
                .padding(top = 4.dp, bottom = 24.dp)
        )

        OutlinedTextField(
            value = state.email,
            onValueChange = { onEvent(AuthEvent.OnEmailChange(it)) },
            label = { Text("Your Email Address") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        PasswordTextField(
            password = state.password,
            onPasswordChange = { onEvent(AuthEvent.OnPasswordChange(it)) },
            isError = state.status == AuthStatus.Error,
            errorMessage = state.errorMessage
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onEvent(AuthEvent.OnLoginClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            enabled = state.email.isNotEmpty() && state.password.isNotEmpty()
        ) {
            Text("Sign In", style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.ExtraBold
            ))

        }
    }
}


@Composable
fun PasswordTextField(
    password: String,
    onPasswordChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String?
) {
    var isPasswordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = onPasswordChange,
        label = { Text("Password") },
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (isPasswordVisible)
                Icons.Default.Visibility
            else
                Icons.Default.VisibilityOff

            val description = if (isPasswordVisible) "Hide password" else "Show password"

            IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                Icon(imageVector = image, contentDescription = description)
            }
        },
        isError = isError,
        supportingText = {
            if (isError && errorMessage!=null )
                Text(errorMessage, style = MaterialTheme.typography.bodySmall)
        },
        modifier = Modifier.fillMaxWidth()
    )
}

