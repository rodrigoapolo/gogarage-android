package com.rodrigoapolo.gogarage.model.dto

import com.google.gson.annotations.SerializedName

data class UserLoginDTO(
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String
)
