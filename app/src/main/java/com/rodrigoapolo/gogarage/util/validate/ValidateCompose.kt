package com.rodrigoapolo.gogarage.util.validate

import android.util.Patterns
import com.google.android.material.textfield.TextInputEditText

abstract class ValidateCompose {

    companion object {
         fun validEmailPatternsEmpty(email: String, msg: String): String? {
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches() || email.isEmpty()) {
                return msg
            }
            return null
        }

        fun camposeNullOrEmpty(value: String, msg: String): String? {
            if (value.isNullOrEmpty()) {
                return msg
            }
            return null
        }

        fun validConfirmPassword(password: String, passwordConfirm: String, msg: String): String? {
            if (password != passwordConfirm) {
                return msg
            }
            return null
        }

    }
}