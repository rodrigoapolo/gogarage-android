package com.rodrigoapolo.gogarage.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.gogarage.model.AgendamentoModel
import com.rodrigoapolo.gogarage.retrofit.ApiGoGarage
import com.rodrigoapolo.gogarage.service.AgendamentosService
import com.rodrigoapolo.gogarage.service.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReserveViewModel (): ViewModel(){

    private var _idUser: MutableLiveData<Long> = MutableLiveData()
    private var _agendamentos: MutableLiveData<List<AgendamentoModel>> = MutableLiveData()
    private var _delete: MutableLiveData<Boolean> = MutableLiveData()

    val idUser: LiveData<Long> = _idUser
    val agendamentos: LiveData<List<AgendamentoModel>> = _agendamentos
    val delete: LiveData<Boolean> = _delete

    val service = ApiGoGarage.createService(UserService::class.java)


    fun setIdUser(idUser: Long) {
        _idUser.value = idUser
    }

    fun getReserva(){
        val service = ApiGoGarage.createService(AgendamentosService::class.java)
        val callback = service.getAgendamentos(_idUser.value!!)
        callback.enqueue(object : Callback<List<AgendamentoModel>>{
            override fun onResponse(
                call: Call<List<AgendamentoModel>>,
                response: Response<List<AgendamentoModel>>
            ) {
                if (response.isSuccessful) {
                    _agendamentos.value = response.body()
                }
            }

            override fun onFailure(call: Call<List<AgendamentoModel>>, t: Throwable) {
                Log.i("APIGARAGE", t.toString() + " error no get agendamentos")
            }


        })
    }

    fun deleteAgendamento(idAgendamento: Long) {
        val service = ApiGoGarage.createService(AgendamentosService::class.java)
        val callback = service.delete(idAgendamento)

        callback.enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                _delete.value = response.isSuccessful
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.i("APIGARAGE", t.toString() + " error no apagar agendamentos")
            }

        })
    }

}
