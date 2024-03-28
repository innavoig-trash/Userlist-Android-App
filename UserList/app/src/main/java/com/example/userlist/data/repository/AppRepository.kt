package com.example.userlist.data.repository

import com.example.userlist.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import com.example.userlist.utils.Result

class AppRepository(private val apiService: ApiService) {
    fun getAllUsers() = flow {
        emit(Result.Loading)
        try {
            val searchedUsers = apiService.getAllUsers()
            emit(Result.Success(searchedUsers))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)

    fun getUserDetail(id: Int) = flow {
        emit(Result.Loading)
        try {
            val detailUser = apiService.getUserDetail(id.toString())
            emit(Result.Success(detailUser))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }.flowOn(Dispatchers.IO)
}