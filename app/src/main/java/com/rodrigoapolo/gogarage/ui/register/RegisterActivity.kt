package com.rodrigoapolo.gogarage.ui.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.material.R.*
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.api.Endpoint
import com.rodrigoapolo.gogarage.databinding.ActivityRegisterBinding
import com.rodrigoapolo.gogarage.model.ResponseRegister
import com.rodrigoapolo.gogarage.model.User
import com.rodrigoapolo.gogarage.model.UserEmail
import com.rodrigoapolo.gogarage.ui.login.LoginActivity
import retrofit2.Callback
import com.rodrigoapolo.gogarage.util.NetworkUtils
import com.rodrigoapolo.gogarage.util.validate.ValidateCPF
import retrofit2.Call
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)
        createListenerData()

        binding

        return setContentView(binding.root)
    }

    private fun createListenerData() {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                validEmail()
            }
        }

        binding.passwordConfirmEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                validConfirmPassword()
            }
        }

        binding.passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                validatePassword()
            }
        }

        binding.nameEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                validateName()
            }
        }

        binding.cpfEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                validateCPF()
            }
        }

        binding.phoneEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                validatePhone()
            }
        }

        binding.buttonConfirm.setOnClickListener {
            validateData()
        }
    }

    private fun validatePassword() {
        if (binding.passwordEditText.text.isNullOrEmpty()) {
            binding.passwordContainer.helperText = "Senha inválida"
        } else {
            binding.passwordContainer.helperText = null
        }
    }

    private fun validateName() {
        if (binding.nameEditText.text.isNullOrEmpty()) {
            binding.nameContainer.helperText = "Nome inválido"
        } else {
            binding.nameContainer.helperText = null
        }
    }

    private fun validateCPF() {
        if (binding.cpfEditText.text.isNullOrEmpty()) {
            binding.cpfContainer.helperText = "CPF inválido"
        } else if (!ValidateCPF.isCPF(binding.cpfEditText.text.toString())) {
            binding.cpfContainer.helperText = "CPF inválido"
        } else {
            binding.cpfContainer.helperText = null
        }
    }

    private fun validatePhone() {
        if (binding.cpfEditText.text.isNullOrEmpty()) {
            binding.phoneContainer.helperText = "Telefone inválido"
        } else {
            binding.phoneContainer.helperText = null
        }
    }

    private fun validEmail() {
        val emailText = binding.emailEditText.text.toString()

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()
            || emailText.isEmpty()
        ) {
            binding.emailContainer.helperText = "E-mail inválido"
        } else {
            validateEmailRequest(emailText)
        }
    }

    private fun validConfirmPassword() {
        if (!binding.passwordEditText.text.isNullOrEmpty()) {
            if (binding.passwordEditText.text.toString() !=
                binding.passwordConfirmEditText.text.toString()
            ) {
                binding.passwordConfirmContainer.helperText = "Senha diferente"
            } else {
                binding.passwordConfirmContainer.helperText = null
            }
        }
    }

    private fun validateData() {

        validEmail()
        validatePassword()
        validateName()
        validateCPF()
        validatePhone()

        if (binding.emailContainer.helperText == null &&
            binding.passwordContainer.helperText == null &&
            binding.passwordConfirmContainer.helperText == null &&
            binding.nameContainer.helperText == null &&
            binding.cpfContainer.helperText == null &&
            binding.phoneContainer.helperText == null
        ) {
            register(
                User(
                    null,
                    binding.nameEditText.text.toString(),
                    binding.emailEditText.text.toString(),
                    binding.passwordEditText.text.toString(),
                    binding.phoneEditText.text.toString(),
                    true,
                    binding.cpfEditText.text.toString(),
                    "",
                )
            )
            createDialog()
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "bad", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateEmailRequest(email: String) {
        val retrofitClient = NetworkUtils.getRetrofitInstance("http://192.168.1.13:8080")
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val userEmail = UserEmail(email)

        val callback = endpoint.validateEmail(
            userEmail
        )

        callback.enqueue(object : Callback<UserEmail> {
            override fun onResponse(call: Call<UserEmail>, response: Response<UserEmail>) {
                if (response.isSuccessful) {
                    if (response.body()?.email == email) {
                        binding.emailContainer.helperText = "E-mail já cadastrado"
                    }
                } else {
                    binding.emailContainer.helperText = null
                }
            }

            override fun onFailure(call: Call<UserEmail>, t: Throwable) {
                Log.i("validateEmail", t.toString())
            }

        })
    }

    private fun register(user: User) {
        val retrofitClient = NetworkUtils.getRetrofitInstance("http://192.168.1.13:8080")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        val callback = endpoint.register(user)

        callback.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                Log.i("register", response.body().toString())
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                Log.i("register", t.toString())
            }

        })
    }

    private fun createDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_register_success, null, false)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)

        view.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            successRegister()
        }

        dialog.show()
    }

    private fun successRegister() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}