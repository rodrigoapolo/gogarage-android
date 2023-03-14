package com.rodrigoapolo.gogarage.ui.home

import android.Manifest.*
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rodrigoapolo.gogarage.BuildConfig
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.api.Endpoint
import com.rodrigoapolo.gogarage.databinding.ActivityHomeBinding
import com.rodrigoapolo.gogarage.model.User
import com.rodrigoapolo.gogarage.model.garage.Address
import com.rodrigoapolo.gogarage.model.garage.Bairro
import com.rodrigoapolo.gogarage.model.garage.Garage
import com.rodrigoapolo.gogarage.model.garage.Situacao
import com.rodrigoapolo.gogarage.ui.home.recyclerview.ItemAdapter
import com.rodrigoapolo.gogarage.util.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = ItemAdapter(listGarage)
        }

        getGarage()

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

    private fun getGarage() {
        val retrofitClient = NetworkUtils.getRetrofitInstance(BuildConfig.PATH)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val village = "Jardim"

        val callback = endpoint.getGarage(
            village
        )

        callback.enqueue(object : Callback<List<Garage>> {
            override fun onResponse(call: Call<List<Garage>>, response: Response<List<Garage>>) {
                if (response.isSuccessful) {
                    Log.i("garageLocal", response.body().toString())
                } else {
                    Log.i("garageLocal", response.body().toString())
                }
            }

            override fun onFailure(call: Call<List<Garage>>, t: Throwable) {
                Log.i("garageLocal", t.toString())
            }

        })
    }
}