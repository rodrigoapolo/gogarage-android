package com.rodrigoapolo.gogarage.model

import com.google.gson.annotations.SerializedName
import com.rodrigoapolo.gogarage.enums.Situacao

data class GarageModel(
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
    @SerializedName("situacao") var situacao: Situacao,
    @SerializedName("latitude") var latitude: String,
    @SerializedName("longitude") var longitude: String,
    @SerializedName("enderecoModel") var endereco: AddressModel,
    @SerializedName("pessoa") var pessoa: UserModel
)
