package com.rodrigoapolo.gogarage.View

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.ViewModel.RegisterAddressGarageViewModel
import com.rodrigoapolo.gogarage.databinding.ActivityRegisterAddressGarageBinding
import com.rodrigoapolo.gogarage.dto.GarageDTO

class RegisterAddressGarageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterAddressGarageBinding
    private lateinit var viewModel: RegisterAddressGarageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterAddressGarageBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)
        viewModel = ViewModelProvider(this)[RegisterAddressGarageViewModel::class.java]
        setContentView(binding.root)

        createListenerData()
        setObserver()
    }

    @SuppressLint("NewApi")
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
            viewModel.validateNeighborhood(
                binding.neighborhoodEditText.text.toString(),
                "Bairro inválido"
            )
            viewModel.validateCity(binding.cityEditText.text.toString(), "Cidade inválida")

            val garage = intent.extras?.getParcelable<GarageDTO>("garage", GarageDTO::class.java) ?: GarageDTO()
                garage.endereco.cep = binding.cepEditText.text.toString()
                garage.endereco.numero = binding.numberEditText.text.toString()
                garage.endereco.complemento = binding.complementEditText.text.toString()
                garage.endereco.logradouro = binding.streetEditText.text.toString()
                garage.endereco.bairro = binding.neighborhoodEditText.text.toString()
                garage.endereco.cidade = binding.cityEditText.text.toString()
                viewModel.doRegister(garage)


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
        viewModel.streetText().observe(this) {
            binding.streetEditText.setText(it)
        }
        viewModel.neighborhood().observe(this) {
            binding.neighborhoodContainer.helperText = it
        }
        viewModel.city().observe(this) {
            binding.cityContainer.helperText = it
        }
        viewModel.neighborhoodText().observe(this) {
            binding.neighborhoodEditText.setText(it)
        }
        viewModel.cityText().observe(this) {
            binding.cityEditText.setText(it)
        }
        viewModel.register().observe(this) {
            if(it){
                createDialog()
            }
        }

    }
    private fun createDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_register_success, null, false)
        view.findViewById<TextView>(R.id.text_message).text = "Parabéns, você concluiu o cadastro da sua Garagem. Torcemos que você tenha uma excelente experiência em nossa plataforma."
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        view.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            successRegister()
        }
        dialog.show()
    }
    private fun successRegister() {
        val intent = Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}