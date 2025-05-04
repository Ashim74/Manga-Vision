package com.droidnova.mangavision.data.repositoryImpl

import com.droidnova.mangavision.data.local.dao.UserDao
import com.droidnova.mangavision.data.local.entities.UserEntity
import com.droidnova.mangavision.data.repository.AuthRepository
import com.droidnova.mangavision.utils.UserSessionManager
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val session: UserSessionManager
) : AuthRepository {

    override suspend fun loginOrRegister(email: String, password: String): Boolean {
        val existing = userDao.getUserByEmail(email)
        return if (existing == null) {
            userDao.insertUser(UserEntity(email = email, password = password))
            session.login(email)
            true
        } else if (existing.password == password) {
            session.login(email)
            true
        } else false
    }

    override suspend fun getLoggedInUser(): String? = session.loggedInEmail.firstOrNull()

    override suspend fun logout() = session.logout()
}
