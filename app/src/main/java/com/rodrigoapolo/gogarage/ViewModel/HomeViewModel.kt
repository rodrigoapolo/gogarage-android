package com.rodrigoapolo.gogarage.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rodrigoapolo.gogarage.model.GarageModel
import com.rodrigoapolo.gogarage.repository.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(private val repository: Repository) : ViewModel() {

    var responseGarageModel: MutableLiveData<Response<List<GarageModel>>> = MutableLiveData()

    fun getGarage(village: String) {
        viewModelScope.launch {
            val response = repository.getGarage(village)
            responseGarageModel.value = response
        }
    }

}