package com.rodrigoapolo.gogarage.ui.home

import android.Manifest
import android.Manifest.*
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.databinding.ActivityHomeBinding
import com.rodrigoapolo.gogarage.model.User
import com.rodrigoapolo.gogarage.model.garage.Address
import com.rodrigoapolo.gogarage.model.garage.Garage
import com.rodrigoapolo.gogarage.ui.home.recyclerview.ItemAdapter
import java.io.IOException
import java.util.*

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var listGarage: MutableList<Garage>
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        getLocationUser()

        listGarage = mutableListOf()
        populate()

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = ItemAdapter(listGarage)
        }

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
                val village = formatNeighborhood(addressesList.toString())

            }
        }
    }

    private fun formatNeighborhood(address: String): String {
        return address.substringAfter("- ").substringBefore(",")
    }

    private fun populate() {
        val garage1 = Garage(
            0,
            true,
            "http//teste",
            "8:00",
            "20:00",
            12.0,
            30.0,
            12.3,
            8.2,
            disponibilidade = false,
            status = true,
            situacao = 1,
            latitude = "0.0000",
            longitude = "1.0000",
            endereco = Address(
                "Rua Penápolis, 394",
                "08564-200",
                "",
                "Vila Coqueral",
                "Poá",
                "SP",
                "394"
            ),
            pessoa = User(
                id = 0,
                name = "Gabriel",
                email = "gabriel.contato2013@outlook.com",
                password = "123456",
                phone = "11977527475",
                status = true,
                cpf = "12345678912",
                cnpj = ""
            )
        )

        listGarage.add(garage1)
        listGarage.add(garage1)
        listGarage.add(garage1)
        listGarage.add(garage1)
        listGarage.add(garage1)

    }
}