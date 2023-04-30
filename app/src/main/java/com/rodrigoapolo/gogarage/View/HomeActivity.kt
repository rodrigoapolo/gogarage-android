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
import com.rodrigoapolo.gogarage.model.GarageModel
import com.rodrigoapolo.gogarage.repository.Repository
import com.rodrigoapolo.gogarage.recyclerview.GarageAdapter
import com.rodrigoapolo.gogarage.util.SecurityPreferences


class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: ActivityHomeBinding
    private lateinit var listGarageModel: MutableList<GarageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val village = SecurityPreferences(applicationContext).getStoredString("village")
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)

        listGarageModel = mutableListOf()

        val repository = Repository()
        val viewModelFactory = com.rodrigoapolo.gogarage.Provider.ViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        if (village != "") {
            setGarageRecyclerView(village)
            Log.i("village", village + "HOME")

        } else {
            Toast.makeText(this, "Não encontramos sua localização", Toast.LENGTH_LONG).show()
        }

        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterGaragemActivity::class.java)
            startActivity(intent)
        }

        return setContentView(binding.root)
    }

    private fun setGarageRecyclerView(village: String) {
        viewModel.getGarage(village)
        viewModel.responseGarageModel.observe(this) { response ->
            if (response.isSuccessful) {
                listGarageModel = response.body() as MutableList<GarageModel>
                binding.recyclerView.apply {
                    layoutManager = GridLayoutManager(applicationContext, 1)
                    adapter = GarageAdapter(listGarageModel)
                }
            }
        }
    }
}