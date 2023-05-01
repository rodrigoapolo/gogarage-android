package com.rodrigoapolo.gogarage.retrofit

import android.util.Log
import com.rodrigoapolo.gogarage.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiViaCep {
    companion object {
       private lateinit var INSTANCE: Retrofit

        private fun getRetrofitInstance() : Retrofit {
            val http = OkHttpClient.Builder().build()

            if (!::INSTANCE.isInitialized) {
                INSTANCE = Retrofit.Builder()
                    .baseUrl(BuildConfig.PATHVIACEP)
                    .client(http)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            Log.i("APIVIACEP", INSTANCE.baseUrl().toString() + " rota do retrofit")
            return INSTANCE
        }

        fun <S> createService( c: Class<S>): S{
            return  getRetrofitInstance().create(c)
        }

    }
}