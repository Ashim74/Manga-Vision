package com.droidnova.mangavision.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class UserSessionManager(private val context: Context) {

    val loggedInEmail: Flow<String?> = context.dataStore.data
        .map { it[LOGGED_IN_EMAIL] }

    suspend fun login(email: String) {
        withContext(Dispatchers.IO) {
            context.dataStore.edit {
                it[LOGGED_IN_EMAIL] = email
            }
        }
    }

    suspend fun logout() {
        withContext(Dispatchers.IO) {
            context.dataStore.edit {
                it.remove(LOGGED_IN_EMAIL)
            }
        }
    }

    companion object {
        val LOGGED_IN_EMAIL = stringPreferencesKey("logged_in_email")
    }
}