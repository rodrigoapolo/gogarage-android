package com.rodrigoapolo.gogarage.View

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.databinding.ActivityShowGarageBinding
import com.rodrigoapolo.gogarage.dto.GarageDTO

class ShowGarageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShowGarageBinding
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShowGarageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)

        val garage = intent.extras?.getParcelable("garage", GarageDTO::class.java)
        Log.i("textoGarage","$garage")
        binding.textGarageAddress.text = "${garage?.endereco?.logradouro} - ${garage?.endereco?.bairro}, ${garage?.endereco?.numero} "
        binding.textGarageHorario.text = "${garage?.horarioInicio} - ${garage?.horarioTermino} "
        binding.textGarageValorHora.text = "R$ ${garage?.valorHora}"
        binding.textGarageValorAdicional.text = "R$ ${garage?.taxaHorario}"
        binding.textGarageSizeGarage.text = "Altura: ${garage?.alturaVaga} Largura: ${garage?.larguraVaga}"
        binding.textGarageCobertura.text = if(garage?.cobertura ?: false) "Sim" else "Não"
    }
}