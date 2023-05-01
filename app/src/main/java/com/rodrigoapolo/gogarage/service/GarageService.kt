package com.rodrigoapolo.gogarage.service

import com.rodrigoapolo.gogarage.dto.GarageDTO
import com.rodrigoapolo.gogarage.model.GarageModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface GarageService {
    @POST("/garagens")
    fun registerGarage(
        @Body
        garageDTO: GarageDTO
    ): Call<GarageDTO>

    @GET("/garagens/get-bairro/{bairro}")
    suspend fun getGarage(
        @Path("bairro") bairro: String
    ) : Response<List<GarageModel>>
}