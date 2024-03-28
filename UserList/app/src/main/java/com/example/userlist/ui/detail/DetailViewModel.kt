package com.example.userlist.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.userlist.utils.Result
import com.example.userlist.data.models.DataItem
import com.example.userlist.data.repository.AppRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailViewModel(private val appRepository: AppRepository, private val id: Int) : ViewModel() {
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _userDetail = MutableLiveData<DataItem?>()
    val userDetail: LiveData<DataItem?> = _userDetail

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    init {
        getUserDetail()
    }

    private fun getUserDetail() {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.getUserDetail(id).collect { result ->
                withContext(Dispatchers.Main) {
                    when (result) {
                        is Result.Loading -> {
                            _isLoading.value = true
                        }

                        is Result.Success -> {
                            _isLoading.value = false
                            _userDetail.value = result.data.data
                        }

                        is Result.Error -> {
                            _isLoading.value = false
                            _message.value = result.error
                        }
                    }
                }
            }
        }
    }
}