package com.rodrigoapolo.gogarage.model.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rodrigoapolo.gogarage.model.garage.Address
import kotlinx.parcelize.Parcelize

@Parcelize
class GarageDTO(
    @SerializedName("id") var id: Long,
    @SerializedName("cobertura") var cobertura: Boolean,
    @SerializedName("horarioInicio") var horarioInicio: String,
    @SerializedName("horarioTermino") var horarioTermino: String,
    @SerializedName("taxaHorario") var taxaHorario: Double,
    @SerializedName("valorHora") var valorHora: Double,
    @SerializedName("alturaVaga") var alturaVaga: Double,
    @SerializedName("larguraVaga") var larguraVaga: Double,
    @SerializedName("latitude") var latitude: String,
    @SerializedName("longitude") var longitude: String,
    @SerializedName("endereco") var endereco: Address,
    @SerializedName("pessoa") var idPssoa: Long?
): Parcelable