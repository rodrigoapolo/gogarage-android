package com.rodrigoapolo.gogarage.model.garage

import com.rodrigoapolo.gogarage.model.User

data class Garage(
    var id: Long,
    var cobertura: Boolean,
    var foto: String,
    var horarioInicio: String,
    var horarioTermino: String,
    var taxaHorario: Double,
    var valorHora: Double,
    var alturaVaga: Double,
    var larguraVaga: Double,
    var disponibilidade: Boolean,
    var status: Boolean,
    var situacao: Int,
    var latitude: String,
    var longitude: String,
    var endereco: Address,
    var pessoa: User
)
