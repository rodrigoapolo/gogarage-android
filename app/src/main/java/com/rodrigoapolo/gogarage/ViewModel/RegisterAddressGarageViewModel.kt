package com.rodrigoapolo.gogarage.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.gogarage.BuildConfig
import com.rodrigoapolo.gogarage.dto.GarageDTO
import com.rodrigoapolo.gogarage.model.ViaCepModel
import com.rodrigoapolo.gogarage.retrofit.RetrofitClient
import com.rodrigoapolo.gogarage.service.GarageService
import com.rodrigoapolo.gogarage.service.ViaCepService
import com.rodrigoapolo.gogarage.util.validate.ValidateCompose
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterAddressGarageViewModel : ViewModel() {
    private val _cep = MutableLiveData<String>()
    private val _number = MutableLiveData<String>()
    private val _street = MutableLiveData<String>()
    private val _neighborhood = MutableLiveData<String>()
    private val _city = MutableLiveData<String>()
    private val _streetText = MutableLiveData<String>()
    private val _neighborhoodText = MutableLiveData<String>()
    private val _cityText = MutableLiveData<String>()
    private val _register = MutableLiveData<Boolean>()

    fun cep(): LiveData<String> {
        return _cep
    }

    fun number(): LiveData<String> {
        return _number
    }

    fun street(): LiveData<String> {
        return _street
    }

    fun neighborhood(): LiveData<String> {
        return _neighborhood
    }

    fun city(): LiveData<String> {
        return _city
    }

    fun streetText(): LiveData<String?> {
        return _streetText
    }
    fun neighborhoodText(): LiveData<String> {
        return _neighborhoodText
    }
    fun cityText(): LiveData<String> {
        return _cityText
    }
    fun register(): LiveData<Boolean> {
        return _register
    }

    fun validateCep(value: String, msg: String) {
        if (ValidateCompose.camposeNullOrEmpty(value, msg) == null) {
            cepRequest(value)
        } else {
            _cep.value = msg
        }

    }

    private fun cepRequest(cep: String) {
        val service =
            RetrofitClient.createService(BuildConfig.PATHVIACEP, ViaCepService::class.java)

        val callback = service.address(cep)

        callback.enqueue(object : Callback<ViaCepModel> {
            override fun onResponse(call: Call<ViaCepModel>, response: Response<ViaCepModel>) {
                _streetText.value = response.body()?.logradouro
                _neighborhoodText.value = response.body()?.bairro
                _cityText.value = response.body()?.uf
            }
            override fun onFailure(call: Call<ViaCepModel>, t: Throwable) {
                Log.i("requestAPI", "$t error Address")
            }
        })
    }

    fun validateNumber(value: String, msg: String) {
        _number.value = ValidateCompose.camposeNullOrEmpty(value, msg)
    }

    fun validateStreet(value: String, msg: String) {
        _street.value = ValidateCompose.camposeNullOrEmpty(value, msg)
    }

    fun validateNeighborhood(value: String, msg: String) {
        _neighborhood.value = ValidateCompose.camposeNullOrEmpty(value, msg)
    }

    fun validateCity(value: String, msg: String) {
        _city.value = ValidateCompose.camposeNullOrEmpty(value, msg)
    }

    fun doRegister(garage: GarageDTO) {
        if(_cep.value == null && _number.value == null && _street.value == null &&
            _neighborhood.value == null && _city.value == null){
            registerGarage(garage)
        }
    }

    private fun registerGarage(garage: GarageDTO) {
        val service = RetrofitClient.createService(BuildConfig.PATH, GarageService::class.java)

        val callback = service.registerGarage(garage)
        Log.i("requestAPI", "${garage}:  Garage")
        callback.enqueue(object : Callback<GarageDTO>{
            override fun onResponse(call: Call<GarageDTO>, response: Response<GarageDTO>) {
                if (response.isSuccessful){
                    _register.value = true
                    Log.i("requestAPI", "$response  OK register Garage")
                }else{
                    Log.i("requestAPI", "$response  error no if register Garage")
                }
            }

            override fun onFailure(call: Call<GarageDTO>, t: Throwable) {
                Log.i("requestAPI", "$t error register Garage")
            }

        })

    }

}