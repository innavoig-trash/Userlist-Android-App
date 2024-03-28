package com.example.userlist.data.remote

import com.example.userlist.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    fun getApiService(): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.REQRES_ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        if (BuildConfig.DEBUG) {
                            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
                        } else {
                            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
                        }
                    )
                    .build()
            )
            .build()
        return retrofit.create(ApiService::class.java)
    }
}