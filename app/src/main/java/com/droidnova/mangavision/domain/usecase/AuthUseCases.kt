package com.droidnova.mangavision.domain.usecase

import com.droidnova.mangavision.data.repository.AuthRepository

data class AuthUseCases(
    val loginOrRegister: LoginOrRegisterUseCase,
    val getLoggedInUser: GetLoggedInUserUseCase,
    val logout: LogoutUseCase
)

class LoginOrRegisterUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke(email: String, password: String) = repo.loginOrRegister(email, password)
}

class GetLoggedInUserUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke() = repo.getLoggedInUser()
}

class LogoutUseCase(private val repo: AuthRepository) {
    suspend operator fun invoke() = repo.logout()
}
