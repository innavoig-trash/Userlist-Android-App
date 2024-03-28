package com.example.userlist.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userlist.di.Injection
import com.example.userlist.ui.main.ui.home.HomeViewModel
import com.example.userlist.ui.main.ui.profile.ProfileViewModel

class MainViewModelFactory(private val context: Context? = null) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(Injection.provideAppRepository()) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(
                Injection.injectSettingsPreferences(context!!),
                Injection.provideFirebaseAuth()
            ) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}