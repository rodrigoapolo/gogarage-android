package com.rodrigoapolo.gogarage.api

import com.rodrigoapolo.gogarage.model.User
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Endpoint {
    @FormUrlEncoded
    @POST("/users")
    fun authenticate(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Call<User>
}