package com.example.userlist.data.remote

import com.example.userlist.data.models.DetailResponse
import com.example.userlist.data.models.ReqresAllUserResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("users")
    suspend fun getAllUsers(): ReqresAllUserResponse

    @GET("users/{id}")
    suspend fun getUserDetail(@Path("id") id: String): DetailResponse
}