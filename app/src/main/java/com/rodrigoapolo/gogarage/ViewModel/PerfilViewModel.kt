package com.rodrigoapolo.gogarage.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.gogarage.dto.UpdateUserDTO
import com.rodrigoapolo.gogarage.dto.UserEmailDTO
import com.rodrigoapolo.gogarage.model.GarageModel
import com.rodrigoapolo.gogarage.model.UserModel
import com.rodrigoapolo.gogarage.retrofit.ApiGoGarage
import com.rodrigoapolo.gogarage.service.GarageService
import com.rodrigoapolo.gogarage.service.UserService
import com.rodrigoapolo.gogarage.util.ValidateCompose
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PerfilViewModel(): ViewModel(){

    private var _idUser: MutableLiveData<Long> = MutableLiveData()
    private var _garages: MutableLiveData<List<GarageModel>> = MutableLiveData()
    private var _delete: MutableLiveData<Boolean> = MutableLiveData()
    private var  _email: MutableLiveData<String> = MutableLiveData()
    private var _msgError: MutableLiveData<String> = MutableLiveData()
    private var _name: MutableLiveData<String> = MutableLiveData()
    private var _phone: MutableLiveData<String> = MutableLiveData()

    val idUser: LiveData<Long> = _idUser
    val garages: LiveData<List<GarageModel>> = _garages
    val delete: LiveData<Boolean> = _delete
    val email: LiveData<String> = _email
    val msgError: LiveData<String> = _msgError
    val name: LiveData<String> = _name
    val phone: LiveData<String> = _phone

    val service = ApiGoGarage.createService(UserService::class.java)

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

    fun validEmail(email: String, msg: String, msgValidateRequest: String) {
        _msgError.value = ValidateCompose.validEmailPatternsEmpty(email, msg)
        if(email == ""){
            _msgError.value = msg
        }else if (_msgError.value == null && email != "") {
            validateEmailRequest(email, msgValidateRequest)
        }
    }

    private fun validateEmailRequest(email: String, msgValidateRequest: String) {
        //val service = ApiGoGarage.createService(UserService::class.java)

        val callback = service.validateEmail(UserEmailDTO(email))

        callback.enqueue(object : Callback<UserEmailDTO> {
            override fun onResponse(call: Call<UserEmailDTO>, response: Response<UserEmailDTO>) {
                if (response.isSuccessful) {
                    _msgError.value = msgValidateRequest
                    Log.i("requestAPI", response.body().toString()+ " validate email do perfil")
                } else {
                    registerEmail(email)
                    Log.i("requestAPI", response.body().toString() + " error validate email do perfil")
                }
            }

            override fun onFailure(call: Call<UserEmailDTO>, t: Throwable) {
                Log.i("requestAPI", t.toString() + " error validate email do perfil")
            }

        })
    }

    private fun registerEmail(email: String) {
        //val service = ApiGoGarage.createService(UserService::class.java)
        val newEmail = UserEmailDTO(email)
        Log.i("requestAPI", "valor do id: ${_idUser.value!!.javaClass.name}")
        val callback = service.updateEmail(_idUser.value!!.toLong(), newEmail)

        callback.enqueue(object: Callback<UserEmailDTO>{
            override fun onResponse(call: Call<UserEmailDTO>, response: Response<UserEmailDTO>) {
                if (response.isSuccessful) {
                    _email.value = response.body()!!.email
                    Log.i("requestAPI", response.body().toString()+ " updateEmail OK")
                } else {
                    Log.i("requestAPI", response.body().toString()+ " error updateEmail ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserEmailDTO>, t: Throwable) {
                Log.i("requestAPI", t.toString() + " error update email")
            }

        })
    }

    fun validateName(name: String, phone: String, msg: String) {
        _msgError.value = ValidateCompose.camposeNullOrEmpty(name, msg)
        if (ValidateCompose.camposeNullOrEmpty(phone, msg) == null && ValidateCompose.camposeNullOrEmpty(name, msg) == null) {
            updateUserAPI(name, phone)
        }
    }

    fun validatePhone(name: String, phone: String, msg: String) {
        _msgError.value = ValidateCompose.camposeNullOrEmpty(phone, msg)
        if (ValidateCompose.camposeNullOrEmpty(phone, msg) == null && ValidateCompose.camposeNullOrEmpty(name, msg) == null) {
            updateUserAPI(name, phone)
        }
    }

    private fun updateUserAPI(name: String, phone: String) {
        val callback = service.updateUser(UpdateUserDTO(_idUser.value!!, name, phone))
        callback.enqueue(object : Callback<UpdateUserDTO> {
            override fun onResponse(call: Call<UpdateUserDTO>, response: Response<UpdateUserDTO>) {
                if (response.isSuccessful) {
                    Log.i(
                        "requestAPI",
                        response.body().toString() + " updateUser ${response.code()}"
                    )
                } else {
                    Log.i(
                        "requestAPI",
                        response.body().toString() + " error updateUser ${response.code()}"
                    )
                }
            }

            override fun onFailure(call: Call<UpdateUserDTO>, t: Throwable) {
                Log.i("requestAPI", t.toString() + " error update user")
            }

        })
    }

    fun getUser() {
        val callback = service.getUser(_idUser.value!!)
        callback.enqueue(object : Callback<UserModel>{
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful){
                    _name.value = response.body()!!.name
                    _phone.value = response.body()!!.phone
                    _email.value = response.body()!!.email
                    Log.i("requestAPI", response.body().toString()+ " get User ok ${response.code()}")
                }else{
                    Log.i("requestAPI", response.body().toString()+ " error get User ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.i("requestAPI", t.toString() + " error get user")
            }

        })
    }

}