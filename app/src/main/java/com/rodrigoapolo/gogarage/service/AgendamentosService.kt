package com.rodrigoapolo.gogarage.service

import com.rodrigoapolo.gogarage.model.AgendamentoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AgendamentosService {

    @POST("/agendamentos")
    fun register(
        @Body agendamento: AgendamentoModel
    ): Call<AgendamentoModel>
}