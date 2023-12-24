package com.utad.myappud3.data_store

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object DataStoreManager {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "PREFERENCES_STORE")

    private const val userKey = "user"
    private const val passwordKey = "password"
    private const val userLoggedKey = "isLogged"

    suspend fun saveUser(context: Context, userName: String, password: String) {
        val userNameKey = stringPreferencesKey(userKey)
        val passwordKey = stringPreferencesKey(passwordKey)

        context.dataStore.edit { editor ->
            editor[userNameKey] = userName
            editor[passwordKey] = password
        }
    }

    suspend fun getUser(context: Context): Flow<String> {
        val userNameKey = stringPreferencesKey(userKey)
        return context.dataStore.data.map { editor ->
            editor[userNameKey] ?: "null"
        }
    }

    suspend fun getPassword(context: Context): Flow<String> {
        val passwordKey = stringPreferencesKey(passwordKey)
        return context.dataStore.data.map { editor ->
            editor[passwordKey] ?: "null"
        }
    }

    suspend fun setUserLogged(context: Context) {
        val userLogged = booleanPreferencesKey(userLoggedKey)
        context.dataStore.edit { editor ->
            editor[userLogged] = true
        }
    }

    suspend fun getIsUserLogged(context: Context): Flow<Boolean> {
        val userLogged = booleanPreferencesKey(userLoggedKey)
        return context.dataStore.data.map { editor ->
            editor[userLogged] ?: false
        }
    }

    suspend fun deleteUser(context: Context) {
        context.dataStore.edit { editor ->
            editor.remove(stringPreferencesKey(userKey))
            editor.remove(stringPreferencesKey(passwordKey))
            editor.remove(stringPreferencesKey(userLoggedKey))
        }
    }

    suspend fun deleteIsLogin(context: Context) {
        context.dataStore.edit { editor ->
            editor.remove(stringPreferencesKey(userLoggedKey))
        }
    }

}