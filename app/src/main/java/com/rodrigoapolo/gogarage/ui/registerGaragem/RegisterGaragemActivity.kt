package com.rodrigoapolo.gogarage.ui.registerGaragem


import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.databinding.ActivityRegisterGaragemBinding
import com.rodrigoapolo.gogarage.ui.RegisterAddressGarage.RegisterAddressGarageActivity

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
            if(!hasFocus){
                viewModel.validateTimeValue(binding.timeValueEditText.text.toString())
            }
        }

        binding.timeRateEditText.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                viewModel.validateTimeRate(binding.timeRateEditText.text.toString())
            }
        }

        binding.garageHeightEditText.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                viewModel.validateHeight(binding.garageHeightEditText.text.toString())
            }
        }

        binding.garageWidthEditText.setOnFocusChangeListener { v, hasFocus ->
            if(!hasFocus){
                viewModel.validateWidth(binding.garageWidthEditText.text.toString())
            }
        }

        binding.buttonContinue.setOnClickListener {
            viewModel.validateTimeValue(binding.timeValueEditText.text.toString())
            viewModel.validateTimeRate(binding.timeRateEditText.text.toString())
            viewModel.validateHeight(binding.garageHeightEditText.text.toString())
            viewModel.validateWidth(binding.garageWidthEditText.text.toString())
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
        viewModel.timeValue().observe(this){
            binding.timeValueContainer.helperText = it
        }
        viewModel.timeRate().observe(this){
            binding.timeRateContainer.helperText = it
        }
        viewModel.height().observe(this){
            binding.garageHeightContainer.helperText = it
        }
        viewModel.width().observe(this){
            binding.garageWidthContainer.helperText = it
        }
        viewModel.register().observe(this){
            if(it != null && it == true) {
                val intent = Intent(this, RegisterAddressGarageActivity::class.java)
                startActivity(intent)
            }
        }
    }
}