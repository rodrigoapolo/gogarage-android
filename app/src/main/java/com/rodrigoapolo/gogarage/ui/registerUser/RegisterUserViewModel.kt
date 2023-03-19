package com.rodrigoapolo.gogarage.ui.registerUser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.gogarage.BuildConfig
import com.rodrigoapolo.gogarage.api.Endpoint
import com.rodrigoapolo.gogarage.model.ResponseRegister
import com.rodrigoapolo.gogarage.model.User
import com.rodrigoapolo.gogarage.model.UserEmail
import com.rodrigoapolo.gogarage.util.NetworkUtils
import com.rodrigoapolo.gogarage.util.validate.ValidateCPF
import com.rodrigoapolo.gogarage.util.validate.ValidateCompose
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterUserViewModel : ViewModel() {

    private var _email = MutableLiveData<String?>()
    private var _password = MutableLiveData<String>()
    private var _passwordConfirm = MutableLiveData<String>()
    private var _name = MutableLiveData<String>()
    private var _cpf = MutableLiveData<String?>()
    private var _phone = MutableLiveData<String>()
    private var _response = MutableLiveData<Boolean>()

    //var email: MutableLiveData<String?> = _email

    fun email(): LiveData<String?> {
        return _email
    }

    fun password(): LiveData<String> {
        return _password
    }

    fun passwordConfirm(): LiveData<String> {
        return _passwordConfirm
    }

    fun name(): LiveData<String> {
        return _name
    }

    fun cpf(): LiveData<String?> {
        return _cpf
    }

    fun phone(): LiveData<String> {
        return _phone
    }

    fun response(): LiveData<Boolean> {
        return _response
    }

    fun validatePhone(phone: String, msg: String) {
        _phone.value = ValidateCompose.camposeNullOrEmpty(phone, msg)
    }

    fun validateCPF(cpf: String, msg: String) {
        _cpf.value = ValidateCompose.camposeNullOrEmpty(cpf, msg)
        if (!ValidateCPF.isCPF(cpf)) {
            _cpf.value = msg
        } else {
            _cpf.value = null
        }
    }

    fun validateName(name: String, msg: String) {
        _name.value = ValidateCompose.camposeNullOrEmpty(name, msg)
    }

    fun validConfirmPassword(
        password: String,
        passwordConfirm: String,
        msg: String,
        msgMasswordInvalid: String
    ) {
        _passwordConfirm.value = ValidateCompose.camposeNullOrEmpty(passwordConfirm, msg)
        if (_password.value == null && _passwordConfirm.value == null) {
            _passwordConfirm.value =
                ValidateCompose.validConfirmPassword(password, passwordConfirm, msgMasswordInvalid)
            _password.value =
                ValidateCompose.validConfirmPassword(password, passwordConfirm, msgMasswordInvalid)
        }
    }

    fun validatePassword(password: String, msg: String) {

        _password.value = ValidateCompose.camposeNullOrEmpty(password, msg)
    }

    fun validEmail(email: String, msg: String) {

        _email.value = ValidateCompose.validEmailPatternsEmpty(email, msg)
        if (_email.value == null) {
            validateEmailRequest(email)
        }
    }

    private fun validateEmailRequest(email: String) {
        val retrofitClient = NetworkUtils.getRetrofitInstance(BuildConfig.PATH)
        val endpoint = retrofitClient.create(Endpoint::class.java)
        val userEmail = UserEmail(email)

        val callback = endpoint.validateEmail(
            userEmail
        )

        callback.enqueue(object : Callback<UserEmail> {
            override fun onResponse(call: Call<UserEmail>, response: Response<UserEmail>) {
                if (response.isSuccessful) {
                    if (response.body()?.email == email) {
                        _email.value = "E-mail já cadastrado"
                        Log.i("validateEmail", response.body().toString())
                    }
                } else {
                    _email.value = null
                    Log.i("validateEmail", response.body().toString())
                }
            }

            override fun onFailure(call: Call<UserEmail>, t: Throwable) {
                Log.i("validateEmail", t.toString())
            }

        })
    }

    fun validateData(name: String, email: String, password: String, phone: String, cpf: String) {
        if (_email.value == null && _password.value == null && _passwordConfirm.value == null &&
            _name.value == null && _cpf.value == null && _phone.value == null
        ) {
            register(User(null, name, email, password, phone, true, cpf, ""))
        } else {
            _response.value = false
        }
    }

    private fun register(user: User) {
        val retrofitClient = NetworkUtils.getRetrofitInstance(BuildConfig.PATH)
        val endpoint = retrofitClient.create(Endpoint::class.java)

        val callback = endpoint.register(user)

        callback.enqueue(object : Callback<ResponseRegister> {
            override fun onResponse(
                call: Call<ResponseRegister>,
                response: Response<ResponseRegister>
            ) {
                _response.value = true
                Log.i("register", response.body().toString())
            }

            override fun onFailure(call: Call<ResponseRegister>, t: Throwable) {
                _response.value = false
                Log.i("register", t.toString())
            }

        })
    }
}