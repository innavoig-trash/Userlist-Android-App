package com.example.userlist.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.userlist.data.remote.ApiConfig
import com.example.userlist.data.repository.AppRepository
import com.example.userlist.utils.SettingsPreferences
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

object Injection {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = SettingsPreferences.SETTINGS_PREFS)

    fun provideAppRepository() = AppRepository(ApiConfig.getApiService())
    fun injectSettingsPreferences(context: Context) =
        SettingsPreferences.getInstance(context.dataStore)

    fun provideFirebaseAuth() = Firebase.auth
    fun provideFirebaseFirestore() = Firebase.firestore
}