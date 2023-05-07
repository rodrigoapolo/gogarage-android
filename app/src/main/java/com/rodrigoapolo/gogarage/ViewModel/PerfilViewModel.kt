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

class PerfilViewModel(): ViewModel(){

    private var _idUser: MutableLiveData<Long> = MutableLiveData()
    private var _garages: MutableLiveData<List<GarageModel>> = MutableLiveData()
    private var _delete: MutableLiveData<Boolean> = MutableLiveData()

    val idUser: LiveData<Long> = _idUser
    val garages: LiveData<List<GarageModel>> = _garages
    val delete: LiveData<Boolean> = _delete

    fun setGarageUser() {
        val service = ApiGoGarage.createService(GarageService::class.java)
        val callback = service.getGarageUser(idUser.value!!)

        callback.enqueue(object : Callback<List<GarageModel>> {
            override fun onResponse(
                call: Call<List<GarageModel>>,
                response: Response<List<GarageModel>>
            ) {
                if (response.isSuccessful) {

                    _garages.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<GarageModel>>, t: Throwable) {
                Log.i("APIGARAGE", t.toString() + " error pegar garagem")
            }

        })
    }

    fun deleteGarage(id: Long) {
        val service = ApiGoGarage.createService(GarageService::class.java)
        val callback = service.deleteGarage(id)
        callback.enqueue(object : Callback<Void> {
            override fun onResponse(
                call: Call<Void>,
                response: Response<Void>
            ) {
                if (response.isSuccessful) {
                    _delete.value = true
                }
            }
            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i("APIGARAGE", t.toString() + " error pegar garagem")
            }

        })

    }

    fun setIdUser(idUser: Long) {
        _idUser.value = idUser
    }

}