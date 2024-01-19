package com.rodrigoapolo.gogarage.dto

import com.google.gson.annotations.SerializedName

data class LoginResponseDTO(
    @SerializedName("id") var id: Long
)