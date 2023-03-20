package com.rodrigoapolo.gogarage.retrofit.service

import com.rodrigoapolo.gogarage.retrofit.model.EmailModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface EmailService {
    @POST("email/sending-email")
    fun sendingEmail(
        @Body
        email: EmailModel
    ): Call<EmailModel>
}