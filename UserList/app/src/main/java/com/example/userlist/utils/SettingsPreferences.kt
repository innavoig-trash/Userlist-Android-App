package com.example.userlist.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.map

class SettingsPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    fun getCurrentUsername() = dataStore.data.map { it[PREFERENCES_USERNAME] ?: PREFERENCES_DEFAULT_VALUE }
    fun getCurrentEmail() = dataStore.data.map { it[PREFERENCES_EMAIL] ?: PREFERENCES_DEFAULT_VALUE }
    fun getCurrentGithubUsername() = dataStore.data.map { it[PREFERENCES_GITHUB_USERNAME] ?: PREFERENCES_DEFAULT_VALUE }
    fun getCurrentNik() = dataStore.data.map { it[PREFERENCES_NIK] ?: PREFERENCES_DEFAULT_VALUE }

    fun getIsUserLoggedIn() = dataStore.data.map {
        it[PREFERENCES_IS_USER_LOGGED_IN] ?: false
    }

    suspend fun setIsUserLoggedIn(isUserLoggedIn: Boolean) {
        dataStore.edit {
            it[PREFERENCES_IS_USER_LOGGED_IN] = isUserLoggedIn
        }
    }

    suspend fun setCurrentUser(
        username: String,
        email: String,
        githubUsername: String,
        nik: String,
    ) {
        dataStore.edit {
            it[PREFERENCES_USERNAME] = username
            it[PREFERENCES_EMAIL] = email
            it[PREFERENCES_GITHUB_USERNAME] = githubUsername
            it[PREFERENCES_NIK] = nik
        }
    }

    suspend fun clearPreferences() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SettingsPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingsPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingsPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }

        const val SETTINGS_PREFS = "settings_preferences"
        const val PREFERENCES_DEFAULT_VALUE = "preferences_default_value"

        val PREFERENCES_IS_USER_LOGGED_IN = booleanPreferencesKey("preferences_is_user_logged_in")

        val PREFERENCES_USERNAME = stringPreferencesKey("preferences_username")
        val PREFERENCES_EMAIL = stringPreferencesKey("preferences_email")
        val PREFERENCES_GITHUB_USERNAME = stringPreferencesKey("preferences_github_username")
        val PREFERENCES_NIK = stringPreferencesKey("preferences_nik")

    }
}