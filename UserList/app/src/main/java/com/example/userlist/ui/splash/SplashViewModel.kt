package com.example.userlist.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.userlist.utils.SettingsPreferences

class SplashViewModel(
    private val settingsPreferences: SettingsPreferences,
) : ViewModel() {
    fun getIsLoggedIn() = settingsPreferences.getIsUserLoggedIn().asLiveData()
}