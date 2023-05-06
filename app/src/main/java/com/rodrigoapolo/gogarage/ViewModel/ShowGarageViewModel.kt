package com.rodrigoapolo.gogarage.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.gogarage.dto.GarageDTO
import com.rodrigoapolo.gogarage.model.AgendamentoModel
import com.rodrigoapolo.gogarage.retrofit.ApiGoGarage
import com.rodrigoapolo.gogarage.service.AgendamentosService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowGarageViewModel : ViewModel(){

    private var _timeStart = MutableLiveData<String>()
    private var _timeEnd = MutableLiveData<String>()
    private var _erroTime = MutableLiveData<String>()
    private var _register = MutableLiveData<Boolean>()

    val timeStart: LiveData<String> = _timeStart
    val timeEnd: LiveData<String> = _timeEnd
    val erroTime: LiveData<String> = _erroTime
    val register: LiveData<Boolean> = _register


    fun formataTimeStart(hour: Int, minute: Int) {
        _timeStart.value = "%02d".format(hour) + ":" + "%02d".format(minute)
    }

    fun formataTimeEnd(hour: Int, minute: Int) {
        _timeEnd.value = "%02d".format(hour) + ":" + "%02d".format(minute)
    }

    fun reserva(msg: String, garage: GarageDTO, id: Long) {
        if (_timeEnd.value != null && _timeStart.value != null){
            val service = ApiGoGarage.createService(AgendamentosService::class.java)

            val agendamento = AgendamentoModel(id, garage.id, _timeStart.value.toString(),
                _timeEnd.value.toString()
            )
            val callback = service.register(agendamento)

            callback.enqueue(object : Callback<AgendamentoModel>{
                override fun onResponse(
                    call: Call<AgendamentoModel>,
                    response: Response<AgendamentoModel>
                ) {
                    if (response.isSuccessful) {
                         _register.value = true
                    } else {
                        _erroTime.value = "erro ao servidor code: ${response.code()}"
                        _register.value = false
                    }
                }

                override fun onFailure(call: Call<AgendamentoModel>, t: Throwable) {
                    Log.i("APIGARAGE", t.toString() + " error no registar agendamento")
                }

            })
        }else{
            _erroTime.value = msg
        }
    }
}