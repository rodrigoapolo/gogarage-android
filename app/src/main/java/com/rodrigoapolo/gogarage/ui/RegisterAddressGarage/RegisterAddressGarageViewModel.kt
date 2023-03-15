package com.rodrigoapolo.gogarage.ui.RegisterAddressGarage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.gogarage.util.validate.ValidateCompose

class RegisterAddressGarageViewModel: ViewModel() {
    private val _cep = MutableLiveData<String>()
    private val _number = MutableLiveData<String>()
    private val _street = MutableLiveData<String>()
    private val _neighborhood = MutableLiveData<String>()
    private val _city = MutableLiveData<String>()
    private val _register= MutableLiveData<Boolean>()

    fun cep(): LiveData<String>{
        return _cep
    }
    fun number(): LiveData<String>{
        return _number
    }
    fun street (): LiveData<String>{
        return _street
    }
    fun neighborhood(): LiveData<String>{
        return _neighborhood
    }
    fun city(): LiveData<String>{
        return _city
    }
    fun register(): LiveData<Boolean>{
        return _register
    }

    fun validateCep(value: String,msg: String){
        _cep.value = ValidateCompose.camposeNullOrEmpty(value, msg)
    }
    fun validateNumber(value: String,msg: String){
        _number.value = ValidateCompose.camposeNullOrEmpty(value, msg)
    }
    fun validateStreet(value: String,msg: String){
        _street.value = ValidateCompose.camposeNullOrEmpty(value, msg)
    }
    fun validateNeighborhood(value: String,msg: String){
        _neighborhood.value = ValidateCompose.camposeNullOrEmpty(value, msg)
    }
    fun validateCity(value: String,msg: String){
        _city.value = ValidateCompose.camposeNullOrEmpty(value, msg)
    }

    fun doRegister(){
        _register.value = _cep.value == null || _number.value == null || _street.value == null || _neighborhood.value == null || _city.value == null
    }

}