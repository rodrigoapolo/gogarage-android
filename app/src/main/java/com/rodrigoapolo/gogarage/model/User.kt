package com.rodrigoapolo.gogarage.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Long?,
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("status")
    var status: Boolean,
    @SerializedName("cpf")
    var cpf: String,
    @SerializedName("cnpj")
    var cnpj: String,
    )
