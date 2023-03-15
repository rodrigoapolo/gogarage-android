package com.rodrigoapolo.gogarage.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    var id: Long?,
    @SerializedName("name")
    var name: String?,
    @SerializedName("email")
    var email: String?,
    @SerializedName("password")
    var password: String?,
    @SerializedName("phone")
    var phone: String?,
    @SerializedName("status")
    var status: Boolean,
    @SerializedName("cpf")
    var cpf: String?,
    @SerializedName("cnpj")
    var cnpj: String?,
    ): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeString(password)
        parcel.writeString(phone)
        parcel.writeByte(if (status) 1 else 0)
        parcel.writeString(cpf)
        parcel.writeString(cnpj)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }
}
