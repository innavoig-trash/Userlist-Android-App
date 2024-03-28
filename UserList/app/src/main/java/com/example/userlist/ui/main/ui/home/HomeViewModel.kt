package com.example.userlist.ui.main.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist.data.models.DataItem
import com.example.userlist.data.repository.AppRepository
import com.example.userlist.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val appRepository: AppRepository) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _allUsers = MutableLiveData<ArrayList<DataItem>>(arrayListOf())
    val allUsers: LiveData<ArrayList<DataItem>> = _allUsers

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        getAllUsers()
    }

    private fun getAllUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getAllUsers().collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }

                    is Result.Success -> {
                        _isLoading.postValue(false)
                        _allUsers.postValue(result.data.data as ArrayList<DataItem>?)
                    }

                    is Result.Error -> {
                        _isLoading.postValue(false)
                        _message.postValue(result.error)
                    }
                }
            }
        }
    }
}