package com.rodrigoapolo.gogarage.model.garage

import com.google.gson.annotations.SerializedName
import com.rodrigoapolo.gogarage.model.User

data class Garage(
    @SerializedName("id") var id: Long,
    @SerializedName("cobertura") var cobertura: Boolean,
    @SerializedName("foto") var foto: String,
    @SerializedName("horarioInicio") var horarioInicio: String,
    @SerializedName("horarioTermino") var horarioTermino: String,
    @SerializedName("taxaHorario") var taxaHorario: Double,
    @SerializedName("valorHora") var valorHora: Double,
    @SerializedName("alturaVaga") var alturaVaga: Double,
    @SerializedName("larguraVaga") var larguraVaga: Double,
    @SerializedName("disponibilidade") var disponibilidade: Boolean,
    @SerializedName("status") var status: Boolean,
    @SerializedName("situacao") var situacao: Int,
    @SerializedName("latitude") var latitude: String,
    @SerializedName("longitude") var longitude: String,
    @SerializedName("endereco") var endereco: Address,
    @SerializedName("pessoa") var pessoa: User
)
