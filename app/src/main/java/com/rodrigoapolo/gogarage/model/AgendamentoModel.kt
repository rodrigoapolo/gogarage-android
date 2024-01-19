package com.rodrigoapolo.gogarage.model

import com.google.gson.annotations.SerializedName

class AgendamentoModel (
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("garage")
    var garagem: GarageModel,
    @SerializedName("data_inicio")
    var dataInicio: String = "",
    @SerializedName("data_final")
    var dataFinal: String = ""
)