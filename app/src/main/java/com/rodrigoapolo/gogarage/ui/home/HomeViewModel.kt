package com.rodrigoapolo.gogarage.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoapolo.gogarage.model.garage.Garage
import com.rodrigoapolo.gogarage.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repository: Repository) : ViewModel() {

    var responseGarage: MutableLiveData<Response<List<Garage>>> = MutableLiveData()

    fun getGarage(village: String) {
        viewModelScope.launch {
            val response = repository.getGarage(village)
            responseGarage.value = response
        }
    }

}