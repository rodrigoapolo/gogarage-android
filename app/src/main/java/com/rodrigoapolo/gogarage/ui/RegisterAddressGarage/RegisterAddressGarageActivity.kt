package com.rodrigoapolo.gogarage.ui.RegisterAddressGarage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.databinding.ActivityRegisterAddressGarageBinding
import com.rodrigoapolo.gogarage.model.dto.GarageDTO

class RegisterAddressGarageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterAddressGarageBinding
    private lateinit var viewModel: RegisterAddressGarageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAddressGarageBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)
        viewModel = ViewModelProvider(this)[RegisterAddressGarageViewModel::class.java]
        setContentView(binding.root)

        val garage = intent.extras?.getParcelable<GarageDTO>("garage")

        if (garage != null) {
           // binding.cepEditText.setText(garage.horarioInicio)
        }

        createListenerData()
        setObserver()
    }

    private fun createListenerData() {
        binding.cepEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.validateCep(binding.cepEditText.text.toString(), "CEP inválido")
            }
        }
        binding.numberEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.validateNumber(binding.numberEditText.text.toString(), "Número inválido")
            }
        }
        binding.streetEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.validateStreet(
                    binding.streetEditText.text.toString(),
                    "Logradouro inválido"
                )
            }
        }
        binding.neighborhoodEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.validateNeighborhood(
                    binding.neighborhoodEditText.text.toString(),
                    "Bairro inválido"
                )
            }
        }
        binding.cityEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.validateCity(binding.cityEditText.text.toString(), "Cidade inválida")
            }
        }
        binding.buttonRegister.setOnClickListener {
            viewModel.validateCep(binding.cepEditText.text.toString(), "CEP inválido")
            viewModel.validateNumber(binding.numberEditText.text.toString(), "Número inválido")
            viewModel.validateStreet(binding.streetEditText.text.toString(), "Logradouro inválido")
            viewModel.validateNeighborhood(binding.neighborhoodEditText.text.toString(), "Bairro inválido")
            viewModel.validateCity(binding.cityEditText.text.toString(), "Cidade inválida")
            viewModel.doRegister()
        }
    }

    private fun setObserver() {
        viewModel.cep().observe(this) {
            binding.cepContainer.helperText = it
        }
        viewModel.number().observe(this) {
            binding.numberContainer.helperText = it
        }
        viewModel.street().observe(this) {
            binding.streetContainer.helperText = it
        }
        viewModel.neighborhood().observe(this) {
            binding.neighborhoodContainer.helperText = it
        }
        viewModel.city().observe(this) {
            binding.cityContainer.helperText = it
        }
        viewModel.register().observe(this) {
            if (it) {
                //TODO:
            }
        }
    }
}