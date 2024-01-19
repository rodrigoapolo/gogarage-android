package com.rodrigoapolo.gogarage.service

import com.rodrigoapolo.gogarage.dto.LoginResponseDTO
import com.rodrigoapolo.gogarage.dto.ResponseRegisterDTO
import com.rodrigoapolo.gogarage.dto.UpdateUserDTO
import com.rodrigoapolo.gogarage.dto.UserEmailDTO
import com.rodrigoapolo.gogarage.dto.UserLoginDTO
import com.rodrigoapolo.gogarage.model.UserModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface UserService {

    @GET("/pessoas/{id}")
    fun getUser(
        @Path("id") id: Long
    ): Call<UserModel>

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

    @PUT("/pessoas")
    fun updateUser(
        @Body userUpdate: UpdateUserDTO
    ): Call<UpdateUserDTO>

    @PUT("/pessoas/upadate/email/{id}")
    fun updateEmail(
        @Path("id") id: Long,
        @Body email: UserEmailDTO
    ): Call<UserEmailDTO>
}