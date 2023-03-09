package com.rodrigoapolo.gogarage.util.validate

import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

abstract class ValidateCompose {

    companion object{
        fun validEmailPatternsEmpty(edit: TextInputEditText): String? {
            val emailText = edit.text.toString()

            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches() || emailText.isEmpty()) {
                return "E-mail inválido"
            }
            return null
        }

        fun validPasswordNullEmpty(edit: TextInputEditText): String?{
            if (edit.text.isNullOrEmpty()) {
                return "Senha inválida"
            }
            return null

        }
    }
}