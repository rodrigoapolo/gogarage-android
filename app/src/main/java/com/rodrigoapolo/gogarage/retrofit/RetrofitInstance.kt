package com.rodrigoapolo.gogarage.retrofit

import com.rodrigoapolo.gogarage.BuildConfig
import com.rodrigoapolo.gogarage.service.UserService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val client = OkHttpClient.Builder().build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.PATH)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: UserService by lazy {
        retrofit.create(UserService::class.java)
    }

}