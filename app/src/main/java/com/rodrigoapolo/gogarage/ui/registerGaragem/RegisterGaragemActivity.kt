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

        binding.timeStart.setOnClickListener{

                var hour = 0
                var minutes = 0
                var onTimeSetListener = TimePickerDialog.OnTimeSetListener { v, hourOfDay, minute ->
                    hour = hourOfDay
                    minutes = minute
                    viewModel.formataTimeStart(hour,minutes)
                }

            val timePickerDialog = TimePickerDialog(this,onTimeSetListener, hour, minutes, true)

            timePickerDialog.setTitle("Selecione o Horario")
            timePickerDialog.show()
        }

        binding.timeEnd.setOnClickListener{

            var hour = 0
            var minutes = 0
            var onTimeSetListener = TimePickerDialog.OnTimeSetListener { v, hourOfDay, minute ->
                hour = hourOfDay
                minutes = minute
                viewModel.formataTimeEnd(hour,minutes)
            }

            //val style = AlertDialog.THEME_HOLO_LIGHT

            val timePickerDialog = TimePickerDialog(this,onTimeSetListener, hour, minutes, true)

            timePickerDialog.setTitle("Selecione o Horario")
            timePickerDialog.show()
        }

        binding.buttonContinue.setOnClickListener {
            val intent = Intent(this, RegisterAddressGarageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setObserver() {
        viewModel.timeStart().observe(this){
                binding.timeStart.text = "Inicio: $it"
        }
        viewModel.timeEnd().observe(this){
            binding.timeEnd.text = "Termino: $it"
        }
    }
}