package com.rodrigoapolo.gogarage.service

import com.rodrigoapolo.gogarage.model.ViaCepModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ViaCepService {
    @GET("{cep}/json/")
    fun address(@Path("cep") cep: String):Call<ViaCepModel>
}