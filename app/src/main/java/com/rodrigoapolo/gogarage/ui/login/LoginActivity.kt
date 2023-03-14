package com.rodrigoapolo.gogarage.ui.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.databinding.ActivityLoginBinding
import com.rodrigoapolo.gogarage.ui.home.HomeActivity
import com.rodrigoapolo.gogarage.ui.registerUser.RegisterUserActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)
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

        viewModel.response().observe(this) {
            var intent = Intent(applicationContext, HomeActivity::class.java)
            intent.putExtra("id", it)
            startActivity(intent)
        }
    }

    private fun createListenerData() {
        binding.editEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.validEmail(binding.editEmail)
            }
        }

        binding.editPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.validPassword(binding.editPassword)
            }
        }

        binding.buttonEnter.setOnClickListener {
            viewModel.validEmail(binding.editEmail)
            viewModel.validPassword(binding.editPassword)
            viewModel.doLogin(binding.editEmail, binding.editPassword)
        }

        binding.textRegister.setOnClickListener {
            val intent = Intent(this, RegisterUserActivity::class.java)
            startActivity(intent)
        }
    }

}