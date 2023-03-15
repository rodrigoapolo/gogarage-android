package com.rodrigoapolo.gogarage.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.databinding.ActivityHomeBinding
import com.rodrigoapolo.gogarage.model.garage.Garage
import com.rodrigoapolo.gogarage.repository.Repository
import com.rodrigoapolo.gogarage.ui.RegisterAddressGarage.RegisterAddressGarageActivity
import com.rodrigoapolo.gogarage.ui.home.recyclerview.ItemAdapter
import com.rodrigoapolo.gogarage.ui.registerGaragem.RegisterGaragemActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var binding: ActivityHomeBinding
    private lateinit var listGarage: MutableList<Garage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val bundle: Bundle? = intent.extras
        val village = bundle?.getString("village")
        val id = bundle?.getLong("id")
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)

        listGarage = mutableListOf()

        val repository = Repository()
        val viewModelFactory = ViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        if (village != null) {
            setGarageRecyclerView(village)
            Log.i("village", village + "HOME")

        } else {
            Toast.makeText(this, "Não encontramos sua localização", Toast.LENGTH_LONG).show()
        }

        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterGaragemActivity::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }

        return setContentView(binding.root)
    }

    private fun setGarageRecyclerView(village: String) {
        viewModel.getGarage(village)
        viewModel.responseGarage.observe(this) { response ->
            if (response.isSuccessful) {
                listGarage = response.body() as MutableList<Garage>
                binding.recyclerView.apply {
                    layoutManager = GridLayoutManager(applicationContext, 1)
                    adapter = ItemAdapter(listGarage)
                }
            }
        }
    }
}