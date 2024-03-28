package com.example.userlist.ui.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userlist.di.Injection
import com.example.userlist.ui.auth.login.LoginViewModel
import com.example.userlist.ui.auth.register.RegisterViewModel

class AuthViewModelFactory(private val context: Context) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            LoginViewModel(
                Injection.injectSettingsPreferences(context),
                Injection.provideFirebaseFirestore(),
                Injection.provideFirebaseAuth()
            ) as T
        } else if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
            RegisterViewModel(
                Injection.injectSettingsPreferences(context),
                Injection.provideFirebaseFirestore(),
                Injection.provideFirebaseAuth(),
            ) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}