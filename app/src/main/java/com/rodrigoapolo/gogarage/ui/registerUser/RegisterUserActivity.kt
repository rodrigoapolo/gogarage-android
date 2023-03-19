package com.rodrigoapolo.gogarage.ui.registerUser

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.databinding.ActivityRegisterUserBinding
import com.rodrigoapolo.gogarage.ui.login.LoginActivity

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterUserBinding
    private lateinit var viewModel: RegisterUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterUserBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)
        viewModel = ViewModelProvider(this)[RegisterUserViewModel::class.java]

        createListenerData()
        setObserver()

        return setContentView(binding.root)
    }

    private fun setObserver() {
        viewModel.email().observe(this) {
            binding.emailContainer.helperText = it
        }
        viewModel.password().observe(this) {
            binding.passwordContainer.helperText = it
        }
        viewModel.passwordConfirm().observe(this) {
            binding.passwordConfirmContainer.helperText = it
        }
        viewModel.name().observe(this) {
            binding.nameContainer.helperText = it
        }
        viewModel.cpf().observe(this) {
            binding.cpfContainer.helperText = it
        }
        viewModel.phone().observe(this) {
            binding.phoneContainer.helperText = it
        }
        viewModel.response().observe(this) {
            if (it) {
                createDialog()
            }
        }
    }

    private fun createListenerData() {
        binding.emailEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                viewModel.validEmail(binding.emailEditText.text.toString(), "E-mail inválido", "E-mail já cadastrado")
            }
        }

        binding.passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.validatePassword(
                    binding.passwordEditText.text.toString(),
                    "Senha inválida"
                )
            }
        }

        binding.passwordConfirmEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                viewModel.validatePassword(
                    binding.passwordEditText.text.toString(),
                    "Senha inválida"
                )
                viewModel.validConfirmPassword(
                    binding.passwordEditText.text.toString(),
                    binding.passwordConfirmEditText.text.toString(),
                    "Senha inválida",
                    "Senha diferente"
                )
            }
        }

        binding.nameEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                viewModel.validateName(binding.nameEditText.text.toString(), "Nome inválido")
            }
        }

        binding.cpfEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                viewModel.validateCPF(binding.cpfEditText.text.toString(), "CPF inválido")
            }
        }

        binding.phoneEditText.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                viewModel.validatePhone(binding.phoneEditText.text.toString(), "Telefone inválido")
            }
        }

        binding.buttonConfirm.setOnClickListener {
            viewModel.validEmail(binding.emailEditText.text.toString(), "E-mail inválido","E-mail já cadastrado")
            viewModel.validatePassword(binding.passwordEditText.text.toString(), "Senha inválida")
            viewModel.validatePassword(binding.passwordEditText.text.toString(), "Senha inválida")
            viewModel.validConfirmPassword(
                binding.passwordEditText.text.toString(),
                binding.passwordConfirmEditText.text.toString(),
                "Senha inválida",
                "Senha diferente"
            )
            viewModel.validateName(binding.nameEditText.text.toString(), "Nome inválido")
            viewModel.validateCPF(binding.cpfEditText.text.toString(), "CPF inválido")
            viewModel.validatePhone(binding.phoneEditText.text.toString(), "Telefone inválido")
            viewModel.validateData(
                binding.nameEditText.text.toString(),
                binding.emailEditText.text.toString(),
                binding.passwordEditText.text.toString(),
                binding.phoneEditText.text.toString(),
                binding.cpfEditText.text.toString(),
            )
        }

        binding.textLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
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