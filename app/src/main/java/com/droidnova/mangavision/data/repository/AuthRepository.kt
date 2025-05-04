package com.droidnova.mangavision.data.repository

interface AuthRepository {
    suspend fun loginOrRegister(email: String, password: String): Boolean
    suspend fun getLoggedInUser(): String?
    suspend fun logout()
}
