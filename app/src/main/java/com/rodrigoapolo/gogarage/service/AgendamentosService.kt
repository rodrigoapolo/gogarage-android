package com.rodrigoapolo.gogarage.service

import com.rodrigoapolo.gogarage.dto.AgendamentoDTO
import com.rodrigoapolo.gogarage.model.AgendamentoModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AgendamentosService {

    @POST("/agendamentos")
    fun register(
        @Body agendamento: AgendamentoDTO
    ): Call<AgendamentoDTO>

    @GET("/agendamentos/{idPessoa}")
    fun getAgendamentos(
        @Path("idPessoa") idUser: Long
    ): Call<List<AgendamentoModel>>

    @DELETE("/agendamentos/{id}")
    fun delete(
        @Path("id") id: Long
    ): Call<Void>
}