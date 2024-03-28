package com.example.userlist.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userlist.di.Injection

class DetailViewModelFactory(
    private val id: Int
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(
                Injection.provideAppRepository(),
                id
            ) as T
        } else
            throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}