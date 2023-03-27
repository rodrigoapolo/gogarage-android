package com.rodrigoapolo.gogarage.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.gogarage.BuildConfig
import com.rodrigoapolo.gogarage.service.UserService
import com.rodrigoapolo.gogarage.dto.LoginResponseDTO
import com.rodrigoapolo.gogarage.dto.UserLoginDTO
import com.rodrigoapolo.gogarage.retrofit.RetrofitClient
import com.rodrigoapolo.gogarage.util.Encryptor
import com.rodrigoapolo.gogarage.util.validate.ValidateCompose
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _email = MutableLiveData<String>()
    private val _password = MutableLiveData<String>()
    private val _response = MutableLiveData<Long>()
    private val _village = MutableLiveData<String>()

    fun village(): LiveData<String> {
        return _village
    }

    fun email(): LiveData<String> {
        return _email
    }

    fun password(): LiveData<String> {
        return _password
    }

    fun response(): LiveData<Long> {
        return _response
    }

    fun setVillage(village: String) {
        _village.value = village
    }

    fun validEmail(email: String, msg: String) {
        _email.value = ValidateCompose.validEmailPatternsEmpty(email, msg)
    }

    fun validPassword(password: String, msg: String) {
        _password.value = ValidateCompose.camposeNullOrEmpty(password, msg)
    }

    fun doLogin(email: String, password: String, msg: String) {
        if (_email.value == null && _password.value == null) {
            val service = RetrofitClient.createService(BuildConfig.PATH, UserService::class.java)
            val passwordEncryptor = Encryptor.encryptorString(password.toString())
            val callback = service.authenticate(UserLoginDTO(email, passwordEncryptor))

            callback.enqueue(object : Callback<LoginResponseDTO> {
                override fun onResponse(
                    call: Call<LoginResponseDTO>,
                    response: Response<LoginResponseDTO>
                ) {
                    if (response.isSuccessful) {
                        _response.value = response.body()?.id

                    } else {
                        _password.value = msg
                    }
                }

                override fun onFailure(call: Call<LoginResponseDTO>, t: Throwable) {
                    Log.i("requestAPI", t.toString() + " error login")
                }
            })
        }
    }
}