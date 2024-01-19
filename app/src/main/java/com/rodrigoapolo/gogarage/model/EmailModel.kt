package com.rodrigoapolo.gogarage.model

import com.google.gson.annotations.SerializedName

data class EmailModel(
    @SerializedName("ownerRef")
    var ownerRef: Long? = 0,
    @SerializedName("emailFrom")
    var emailFrom: String = "",
    @SerializedName("emailTo")
    var emailTo: String? = "",
    @SerializedName("subject")
    var subject: String = "",
    @SerializedName("text")
    var text: String = ""
)