package com.rodrigoapolo.gogarage.model

import com.google.gson.annotations.SerializedName

data class UserLogin(
    @SerializedName("email")
    var email: String,
    @SerializedName("password")
    var password: String
)
