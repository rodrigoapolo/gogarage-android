package com.rodrigoapolo.gogarage.repository

import com.rodrigoapolo.gogarage.model.garage.Garage
import com.rodrigoapolo.gogarage.util.RetrofitInstance
import retrofit2.Response

class Repository {

    suspend fun getGarage(village: String): Response<List<Garage>> {
        return RetrofitInstance.api.getGarage(village)
    }

}