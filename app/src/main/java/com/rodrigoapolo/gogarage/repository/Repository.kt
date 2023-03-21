package com.rodrigoapolo.gogarage.repository

import com.rodrigoapolo.gogarage.model.GarageModel
import com.rodrigoapolo.gogarage.retrofit.RetrofitInstance
import retrofit2.Response

class Repository {

    suspend fun getGarage(village: String): Response<List<GarageModel>> {
        return RetrofitInstance.api.getGarage(village)
    }

}