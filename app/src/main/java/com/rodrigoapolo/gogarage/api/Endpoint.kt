package com.rodrigoapolo.gogarage.api

import com.rodrigoapolo.gogarage.model.dto.LoginResponseDTO
import com.rodrigoapolo.gogarage.model.dto.UserLoginDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Endpoint {
    @POST("/pessoas/login")
    fun authenticate(
        @Body requestLogin: UserLoginDTO
    ) : Call<LoginResponseDTO>
}