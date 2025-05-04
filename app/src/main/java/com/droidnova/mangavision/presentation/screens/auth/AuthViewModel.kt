package com.droidnova.mangavision.presentation.screens.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidnova.mangavision.domain.data.AuthStatus
import com.droidnova.mangavision.domain.usecase.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authUseCases: AuthUseCases
) : ViewModel() {

    var state by mutableStateOf(AuthState())
        private set

    init {
        checkSession()
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.OnEmailChange -> {
                state = state.copy(email = event.email)
            }
            is AuthEvent.OnPasswordChange -> {
                state = state.copy(password = event.password)
            }
            AuthEvent.OnLoginClick -> {
                login(state.email, state.password)
            }

            AuthEvent.OnLogout -> {
                logout()
            }
        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            state = state.copy(status = AuthStatus.Loading)
            val result = authUseCases.loginOrRegister(email, password)
            state = if (result) {
                state.copy(status = AuthStatus.Authenticated)
            } else {
                state.copy(status = AuthStatus.Error, errorMessage = "Invalid Password")
            }
        }
    }

    private fun logout(){
        viewModelScope.launch {
            authUseCases.logout()
            state = state.copy(status = AuthStatus.Unauthenticated)
        }
    }

    private fun checkSession() {
        viewModelScope.launch {
            var status = AuthStatus.Unauthenticated
            var email = ""
            try {
                val user = authUseCases.getLoggedInUser()
                user?.let {
                    email = it
                    status = AuthStatus.Authenticated
                }
            }finally {
                state = state.copy(
                    email = email,
                    status = status
                )
            }
        }
    }
}
