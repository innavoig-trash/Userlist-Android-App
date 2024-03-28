package com.example.userlist.ui.main.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.userlist.utils.SettingsPreferences
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val settingsPreferences: SettingsPreferences,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {
    fun getCurrentUsername() = settingsPreferences.getCurrentUsername().asLiveData()
    fun getCurrentNik() = settingsPreferences.getCurrentNik().asLiveData()
    fun getCurrentEmail() = settingsPreferences.getCurrentEmail().asLiveData()
    fun getCurrentGithubUsername() = settingsPreferences.getCurrentGithubUsername().asLiveData()

    fun logoutUser() {
        viewModelScope.launch {
            firebaseAuth.signOut()
            settingsPreferences.clearPreferences()
        }
    }
}