package com.rodrigoapolo.gogarage.util

import java.math.BigInteger
import java.security.MessageDigest

class Encryptor private constructor(){
    companion object{
        fun encryptorString(password: String):String {
            var md: MessageDigest
            try {
                md = MessageDigest.getInstance("SHA-1")
                var hash = BigInteger(1,md.digest(password.toByteArray()))
                return hash. toString(16)
            }catch (e: Exception){
                return password
            }
        }
    }
}