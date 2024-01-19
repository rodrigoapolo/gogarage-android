package com.rodrigoapolo.gogarage.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.gogarage.model.GarageModel
import com.rodrigoapolo.gogarage.retrofit.ApiGoGarage
import com.rodrigoapolo.gogarage.service.GarageService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel() : ViewModel() {

    private var _garages: MutableLiveData<List<GarageModel>> = MutableLiveData()

    val garages: LiveData<List<GarageModel>> = _garages

    fun setGarage(village: String) {
        val service = ApiGoGarage.createService(GarageService::class.java)
        val callback = service.getGarage(village)

        callback.enqueue(object : Callback<List<GarageModel>> {
            override fun onResponse(
                call: Call<List<GarageModel>>,
                response: Response<List<GarageModel>>
            ) {
                if (response.isSuccessful) {
                    _garages.value = response.body()
                    Log.i("village", "bairro: $village HOMEViewModel lista: ${response.body().toString()}")
                }else{
                    Log.i("village", "bairro: $village HOMEViewModel msg: ${response.message()}  error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<GarageModel>>, t: Throwable) {
                Log.i("APIGARAGE", t.toString() + " error pegar garagem")
            }

        })
    }

}