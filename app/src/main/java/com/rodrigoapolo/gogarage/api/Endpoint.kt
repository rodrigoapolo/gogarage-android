package com.rodrigoapolo.gogarage.api

import com.rodrigoapolo.gogarage.model.User
import com.rodrigoapolo.gogarage.model.UserLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface Endpoint {
    @POST("/pessoas/login")
    fun authenticate(
        @Body requestLogin: UserLogin
    ) : Call<User>
}