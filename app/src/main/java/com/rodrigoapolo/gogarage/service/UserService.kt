package com.rodrigoapolo.gogarage.service

import com.rodrigoapolo.gogarage.dto.ResponseRegisterDTO
import com.rodrigoapolo.gogarage.model.UserModel
import com.rodrigoapolo.gogarage.dto.UserEmailDTO
import com.rodrigoapolo.gogarage.dto.LoginResponseDTO
import com.rodrigoapolo.gogarage.dto.UserLoginDTO
import com.rodrigoapolo.gogarage.model.GarageModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {

    @POST("/pessoas/login")
    fun authenticate(
        @Body requestLogin: UserLoginDTO
    ): Call<LoginResponseDTO>

    @POST("/pessoas/valid-email")
    fun validateEmail(
        @Body userEmailDTO: UserEmailDTO
    ): Call<UserEmailDTO>

    @POST("/pessoas")
    fun register(
        @Body userModel: UserModel
    ) : Call<ResponseRegisterDTO>

}