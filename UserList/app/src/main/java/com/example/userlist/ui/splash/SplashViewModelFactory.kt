package com.example.userlist.ui.splash

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userlist.di.Injection

class SplashViewModelFactory(private val context: Context) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SplashViewModel::class.java)) {
            SplashViewModel(
                Injection.injectSettingsPreferences(context),
            ) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}