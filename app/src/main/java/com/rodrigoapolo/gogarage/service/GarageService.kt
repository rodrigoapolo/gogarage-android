package com.rodrigoapolo.gogarage.service

import com.rodrigoapolo.gogarage.dto.GarageDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface GarageService {
    @POST("/garagens")
    fun registerGarage(
        @Body
        garageDTO: GarageDTO
    ): Call<GarageDTO>
}