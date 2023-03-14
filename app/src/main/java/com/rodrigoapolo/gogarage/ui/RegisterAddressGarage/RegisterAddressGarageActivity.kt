package com.rodrigoapolo.gogarage.ui.RegisterAddressGarage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.databinding.ActivityRegisterAddressGarageBinding

class RegisterAddressGarageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterAddressGarageBinding
    private lateinit var viewModel: RegisterAddressGarageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAddressGarageBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)
        viewModel = ViewModelProvider(this)[RegisterAddressGarageViewModel::class.java]
        setContentView(binding.root)
    }
}