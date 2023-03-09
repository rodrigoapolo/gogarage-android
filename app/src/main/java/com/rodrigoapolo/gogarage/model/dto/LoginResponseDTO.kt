package com.rodrigoapolo.gogarage.model.dto

import com.google.gson.annotations.SerializedName

data class LoginResponseDTO(
    @SerializedName("id") var id: Long
)