package com.rodrigoapolo.gogarage.ui.home

import android.Manifest.permission
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.databinding.ActivityHomeBinding
import com.rodrigoapolo.gogarage.model.garage.Garage
import com.rodrigoapolo.gogarage.repository.Repository
import com.rodrigoapolo.gogarage.ui.home.recyclerview.ItemAdapter
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var binding: ActivityHomeBinding
    private lateinit var listGarage: MutableList<Garage>
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var village: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationUser()
        listGarage = mutableListOf()

        val repository = Repository()
        val viewModelFactory = ViewModelProvider(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        viewModel.getGarage("Jardim Débora")
        viewModel.responseGarage.observe(this) { response ->
            if (response.isSuccessful) {
                Log.i("garageLocal", response.body().toString() + "RESPONSE")
                listGarage = response.body() as MutableList<Garage>
            }
        }

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = ItemAdapter(listGarage)
        }

        // TODO FAZER APARECER OS DADOS NO RECYCLER-VIEW

        return setContentView(binding.root)
    }

    private fun getLocationUser() {
        val task = fusedLocationClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(
                    this,
                    permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        task.addOnSuccessListener {
            if (it != null) {

                val geocoder = Geocoder(this, Locale.getDefault())
                val addressesList = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                village = formatNeighborhood(addressesList.toString())
            }
        }
    }

    private fun formatNeighborhood(address: String): String {
        return address.substringAfter("- ").substringBefore(",")
    }
}