package com.rodrigoapolo.gogarage.View

import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.ViewModel.ShowGarageViewModel
import com.rodrigoapolo.gogarage.databinding.ActivityShowGarageBinding
import com.rodrigoapolo.gogarage.dto.GarageDTO
import com.rodrigoapolo.gogarage.util.SecurityPreferences

class ShowGarageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowGarageBinding
    private lateinit var garage: GarageDTO
    private lateinit var viewModel: ShowGarageViewModel
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowGarageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[ShowGarageViewModel::class.java]
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)
        garage = intent.extras?.getParcelable("garage", GarageDTO::class.java)!!

        setObserver()
        createListenerData()
    }

    private fun createListenerData() {
        binding.textTimeGarageStart.setOnClickListener {

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

        binding.textTimeGarageEnd.setOnClickListener {

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

        binding.buttonVaga.setOnClickListener{
            val id = SecurityPreferences(applicationContext).getStoredInt("id").toLong()
            viewModel.reserva("Informe o horario", garage, id)
        }
    }

    private fun setObserver() {
        Log.i("textoGarage","$garage")
        binding.textGarageAddress.text = "${garage?.endereco?.logradouro}, ${garage?.endereco?.numero} "
        binding.textGarageHorario.text = "${garage?.horarioInicio} - ${garage?.horarioTermino} "
        binding.textGarageValorHora.text = "R$ ${garage?.valorHora}"
        binding.textGarageValorAdicional.text = "R$ ${garage?.taxaHorario}"
        binding.textGarageSizeGarage.text = "Alt: ${garage?.alturaVaga} Lar: ${garage?.larguraVaga}"
        binding.textGarageCobertura.text = if(garage?.cobertura ?: false) "Sim" else "Não"

        viewModel.timeStart.observe(this){
            binding.textTimeGarageStart.text = it
        }
        viewModel.timeEnd.observe(this){
            binding.textTimeGarageEnd.text = it
        }
        viewModel.erroTime.observe(this){
            binding.textErroTime.text = it
        }
        viewModel.register.observe(this){
            if(it){
                createDialog()
            }
        }

    }

    private fun createDialog() {
        val view = layoutInflater.inflate(R.layout.bottom_sheet_register_success, null, false)
        view.findViewById<TextView>(R.id.title).text = "Agendado com sucesso"
        view.findViewById<TextView>(R.id.text_message).text = "Parabéns, você concluiu o Agendamento da Garagem. Torcemos que você tenha uma excelente experiência em nossa plataforma."
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        view.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            successRegister()
        }
        dialog.show()
    }
    private fun successRegister() {
        startActivity(Intent(applicationContext, HomeActivity::class.java))
        finish()
    }
}