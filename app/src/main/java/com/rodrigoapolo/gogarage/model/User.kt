package com.rodrigoapolo.gogarage.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String,
    @SerializedName("id")
    var id: Long,
    @SerializedName("cpf")
    var cpf: String,
    @SerializedName("telephone")
    var telephone: String
)
