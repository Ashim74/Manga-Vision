package com.droidnova.mangavision.presentation.screens.auth

sealed class AuthEvent {
    data class OnEmailChange(val email: String) : AuthEvent()
    data class OnPasswordChange(val password: String) : AuthEvent()
    data object OnLoginClick : AuthEvent()
}
