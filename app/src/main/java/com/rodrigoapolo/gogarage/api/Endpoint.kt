package com.rodrigoapolo.gogarage.api

import com.rodrigoapolo.gogarage.model.ResponseRegister
import com.rodrigoapolo.gogarage.model.User
import com.rodrigoapolo.gogarage.model.UserEmail
import com.rodrigoapolo.gogarage.model.dto.LoginResponseDTO
import com.rodrigoapolo.gogarage.model.dto.UserLoginDTO
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface Endpoint {

    @POST("/pessoas/login")
    fun authenticate(
        @Body requestLogin: UserLoginDTO
    ): Call<LoginResponseDTO>

    @POST("/pessoas/valid-email")
    fun validateEmail(
        @Body userEmail: UserEmail
    ): Call<UserEmail>

    @POST("/pessoas")
    fun register(
        @Body user: User
    ) : Call<ResponseRegister>

//    @GET("/garagens/get-bairro/")
//    fun getGarage() {
//
//    }
}