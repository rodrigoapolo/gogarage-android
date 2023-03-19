package com.rodrigoapolo.gogarage.retrofit.retrofit

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitClient {
    companion object {
       private lateinit var INSTANCE: Retrofit

        fun getRetrofitInstance(path: String) : Retrofit {
            val http = OkHttpClient.Builder().build()

                INSTANCE = Retrofit.Builder()
                    .baseUrl(path)
                    .client(http)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            Log.i("requestAPI", INSTANCE.baseUrl().toString() + "error")

            return INSTANCE
        }

        fun <S> createService(path: String, c: Class<S>): S{
            return  getRetrofitInstance(path).create(c)
        }

    }
}