package com.rodrigoapolo.gogarage.service

import com.rodrigoapolo.gogarage.dto.ResponseRegisterDTO
import com.rodrigoapolo.gogarage.model.UserModel
import com.rodrigoapolo.gogarage.dto.UserEmailDTO
import com.rodrigoapolo.gogarage.dto.LoginResponseDTO
import com.rodrigoapolo.gogarage.dto.UserLoginDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


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