package com.rodrigoapolo.gogarage.util

import com.rodrigoapolo.gogarage.BuildConfig
import com.rodrigoapolo.gogarage.retrofit.service.Endpoint
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

    val api: Endpoint by lazy {
        retrofit.create(Endpoint::class.java)
    }

}