package com.rodrigoapolo.gogarage.model.garage

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    @SerializedName("logradouro") var logradouro: String,
    @SerializedName("cep") var cep: String,
    @SerializedName("complemento") var complemento: String,
    @SerializedName("bairro") var bairro: String,
    @SerializedName("cidade") var cidade: String,
    @SerializedName("uf") var uf: String,
    @SerializedName("numero") var numero: String
): Parcelable
