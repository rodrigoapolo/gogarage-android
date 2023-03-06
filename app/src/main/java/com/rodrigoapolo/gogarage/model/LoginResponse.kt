package com.rodrigoapolo.gogarage.model

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("id") var id: Long
)