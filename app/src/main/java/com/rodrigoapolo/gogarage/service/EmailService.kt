package com.rodrigoapolo.gogarage.service

import com.rodrigoapolo.gogarage.model.EmailModel
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