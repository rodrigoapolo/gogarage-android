package com.rodrigoapolo.gogarage.View

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.ViewModel.HomeViewModel
import com.rodrigoapolo.gogarage.databinding.ActivityHomeBinding
import com.rodrigoapolo.gogarage.dto.GarageDTO
import com.rodrigoapolo.gogarage.model.GarageModel
import com.rodrigoapolo.gogarage.recyclerview.GarageAdapter
import com.rodrigoapolo.gogarage.recyclerview.OnItemClickListener
import com.rodrigoapolo.gogarage.util.SecurityPreferences


class HomeActivity : AppCompatActivity(), OnItemClickListener {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val village = SecurityPreferences(applicationContext).getStoredString("village")
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)

        createListenerData()

        if (village != "") {
            setRecyclerViewGarage()
            getRecyclerViewgarage()
            Log.i("village", village + "HOME")

        } else {
            Toast.makeText(this, "Não encontramos sua localização", Toast.LENGTH_LONG).show()
        }



        return setContentView(binding.root)
    }

    private fun createListenerData() {
        binding.imaUser.setOnClickListener{
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getRecyclerViewgarage() {
        val village = SecurityPreferences(applicationContext).getStoredString("village")
        viewModel.setGarage(village)
    }

    private fun setRecyclerViewGarage() {
        viewModel.garages.observe(this){
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(applicationContext, 1)
                adapter = GarageAdapter(viewModel.garages.value!!, this@HomeActivity)
            }
        }
    }

    override fun onItemClick(garageModel: GarageModel) {
        val garage: GarageDTO = GarageDTO(
            garageModel.id,
            garageModel.cobertura,
            garageModel.horarioInicio,
            garageModel.horarioTermino,
            garageModel.taxaHorario,
            garageModel.valorHora,
            garageModel.alturaVaga,
            garageModel.larguraVaga,
            "",
            "",
            garageModel.endereco,
            garageModel.pessoa.id
        )
        val intent = Intent(this, ShowGarageActivity::class.java)
        val putExtra = intent.putExtra("garage", garage)
        startActivities(arrayOf(intent))
    }
}