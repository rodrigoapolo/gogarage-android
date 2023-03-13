package com.rodrigoapolo.gogarage.model.garage

data class Address(
    var logradouro: String,
    var cep: String,
    var complemento: String,
    var bairro: String,
    var cidade: String,
    var uf: String,
    var numero: String
)
