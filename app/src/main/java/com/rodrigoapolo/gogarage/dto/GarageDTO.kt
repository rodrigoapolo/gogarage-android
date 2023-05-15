package com.rodrigoapolo.gogarage.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.rodrigoapolo.gogarage.model.AddressModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class GarageDTO(
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("cobertura")
    var cobertura: Boolean = false,
    @SerializedName("horarioInicio")
    var horarioInicio: String = "",
    @SerializedName("horarioTermino")
    var horarioTermino: String = "",
    @SerializedName("taxaHorario")
    var taxaHorario: Double = 0.0,
    @SerializedName("valorHora")
    var valorHora: Double = 0.0,
    @SerializedName("alturaVaga")
    var alturaVaga: Double = 0.0,
    @SerializedName("larguraVaga")
    var larguraVaga: Double = 0.0,
    @SerializedName("latitude")
    var latitude: String = "",
    @SerializedName("longitude")
    var longitude: String = "",
    @SerializedName("endereco")
    var endereco: AddressModel = AddressModel(),
    @SerializedName("idPssoa")
    var idPssoa: Long = 0
): Parcelable