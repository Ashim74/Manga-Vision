package com.droidnova.mangavision.presentation.screens.auth

import com.droidnova.mangavision.domain.data.AuthStatus

data class AuthState(
    val email: String = "",
    val password: String = "",
    val status: AuthStatus = AuthStatus.Idle,
    val errorMessage: String? = null
)
