package com.rodrigoapolo.gogarage.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import androidx.core.app.ActivityCompat
import com.rodrigoapolo.gogarage.model.garage.Garage
import com.rodrigoapolo.gogarage.util.RetrofitInstance
import retrofit2.Response
import java.util.*

class Repository {

    suspend fun getGarage(village: String): Response<List<Garage>> {
        return RetrofitInstance.api.getGarage(village)
    }

}