package com.rodrigoapolo.gogarage.Activity


import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.ViewModel.RegisterGaragemViewModel
import com.rodrigoapolo.gogarage.databinding.ActivityRegisterGaragemBinding
import com.rodrigoapolo.gogarage.dto.GarageDTO
import com.rodrigoapolo.gogarage.model.AddressModel
import com.rodrigoapolo.gogarage.util.SecurityPreferences

class RegisterGaragemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterGaragemBinding
    private lateinit var viewModel: RegisterGaragemViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterGaragemBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)
        viewModel = ViewModelProvider(this)[RegisterGaragemViewModel::class.java]
        setContentView(binding.root)


        createListenerData()
        setObserver()
    }

    private fun createListenerData() {

        binding.timeStart.setOnClickListener {

            var hour = 0
            var minutes = 0
            var onTimeSetListener = TimePickerDialog.OnTimeSetListener { v, hourOfDay, minute ->
                hour = hourOfDay
                minutes = minute
                viewModel.formataTimeStart(hour, minutes)
            }

            val timePickerDialog = TimePickerDialog(this, onTimeSetListener, hour, minutes, true)

            timePickerDialog.setTitle("Selecione o Horario")
            timePickerDialog.show()
        }

        binding.timeEnd.setOnClickListener {

            var hour = 0
            var minutes = 0
            var onTimeSetListener = TimePickerDialog.OnTimeSetListener { v, hourOfDay, minute ->
                hour = hourOfDay
                minutes = minute
                viewModel.formataTimeEnd(hour, minutes)
            }

            //val style = AlertDialog.THEME_HOLO_LIGHT

            val timePickerDialog = TimePickerDialog(this, onTimeSetListener, hour, minutes, true)

            timePickerDialog.setTitle("Selecione o Horario")
            timePickerDialog.show()
        }

        binding.timeValueEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.validateTimeValue(
                    binding.timeValueEditText.text.toString(),
                    "Valor inválido"
                )
            }
        }

        binding.timeRateEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.validateTimeRate(
                    binding.timeRateEditText.text.toString(),
                    "Valor inválido"
                )
            }
        }

        binding.garageHeightEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.validateHeight(
                    binding.garageHeightEditText.text.toString(),
                    "Valor inválido"
                )
            }
        }

        binding.garageWidthEditText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                viewModel.validateWidth(
                    binding.garageWidthEditText.text.toString(),
                    "Valor inválido"
                )
            }
        }

        binding.buttonContinue.setOnClickListener {
            viewModel.validateTimeValue(binding.timeValueEditText.text.toString(), "Valor inválido")
            viewModel.validateTimeRate(binding.timeRateEditText.text.toString(), "Valor inválido")
            viewModel.validateHeight(binding.garageHeightEditText.text.toString(), "Valor inválido")
            viewModel.validateWidth(binding.garageWidthEditText.text.toString(), "Valor inválido")
            viewModel.doContinue()
        }
    }

    private fun setObserver() {
        viewModel.timeStart().observe(this) {
            binding.timeStart.text = "Inicio: $it"
        }
        viewModel.timeEnd().observe(this) {
            binding.timeEnd.text = "Termino: $it"
        }
        viewModel.timeValue().observe(this) {
            binding.timeValueContainer.helperText = it
        }
        viewModel.timeRate().observe(this) {
            binding.timeRateContainer.helperText = it
        }
        viewModel.height().observe(this) {
            binding.garageHeightContainer.helperText = it
        }
        viewModel.width().observe(this) {
            binding.garageWidthContainer.helperText = it
        }
        viewModel.register().observe(this) {
            if (it != null && it == true) {
                val id = SecurityPreferences(applicationContext).getStoredInt("id").toLong()
                val garage: GarageDTO = GarageDTO(
                    0,
                    binding.switchRoof.isChecked,
                    viewModel.timeStart().value.toString(),
                    viewModel.timeEnd().value.toString(),
                    binding.timeRateEditText.text.toString().toDouble(),
                    binding.timeValueEditText.text.toString().toDouble(),
                    binding.garageHeightEditText.text.toString().toDouble(),
                    binding.garageWidthEditText.text.toString().toDouble(),
                    "",
                    "",
                    AddressModel("", "", "", "", "", "", ""),
                    id
                )

                val intent = Intent(this, RegisterAddressGarageActivity::class.java)
                val putExtra = intent.putExtra("garage", garage)
                startActivity(intent)
            }
        }
    }
}