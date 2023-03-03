package com.rodrigoapolo.gogarage

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rodrigoapolo.gogarage.databinding.ActivityRegisterBinding
import com.rodrigoapolo.gogarage.validation.Cpf

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

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
        } else if (!Cpf.isCPF(binding.cpfEditText.text.toString())){
            binding.cpfContainer.helperText = "CPF inválido"
        }else{
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

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches() || emailText.isEmpty()) {
            binding.emailContainer.helperText = "E-mail inválido"
        } else {
            binding.emailContainer.helperText = null

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
//            val intent = Intent(this, HomeActivity::class.java)
//            startActivity(intent)
            Toast.makeText(this, "ok", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "bad", Toast.LENGTH_SHORT).show()
        }
    }
}