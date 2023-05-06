package com.rodrigoapolo.gogarage.model

import com.google.gson.annotations.SerializedName

data class AgendamentoModel (
    @SerializedName("idPessoa")
    var idPessoa: Long = 0,
    @SerializedName("idGaragem")
    var idGaragem: Long = 0,
    @SerializedName("data_inicio")
    var dataInicio: String = "",
    @SerializedName("data_final")
    var dataFinal: String = ""
)