package com.rodrigoapolo.gogarage.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rodrigoapolo.gogarage.BuildConfig
import com.rodrigoapolo.gogarage.service.UserService
import com.rodrigoapolo.gogarage.dto.ResponseRegisterDTO
import com.rodrigoapolo.gogarage.model.UserModel
import com.rodrigoapolo.gogarage.dto.UserEmailDTO
import com.rodrigoapolo.gogarage.model.EmailModel
import com.rodrigoapolo.gogarage.retrofit.RetrofitClient
import com.rodrigoapolo.gogarage.service.EmailService
import com.rodrigoapolo.gogarage.util.Encryptor
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

    fun validEmail(email: String, msg: String, msgValidateRequest: String) {

        _email.value = ValidateCompose.validEmailPatternsEmpty(email, msg)
        if (_email.value == null) {
            validateEmailRequest(email, msgValidateRequest)
        }
    }

    private fun validateEmailRequest(email: String, msgValidateRequest: String) {
        val service = RetrofitClient.createService(BuildConfig.PATH, UserService::class.java)

        val callback = service.validateEmail(UserEmailDTO(email))

        callback.enqueue(object : Callback<UserEmailDTO> {
            override fun onResponse(call: Call<UserEmailDTO>, response: Response<UserEmailDTO>) {
                if (response.isSuccessful) {
                        _email.value = msgValidateRequest
                        Log.i("requestAPI", response.body().toString()+ " validate email")
                } else {
                    _email.value = null
                    Log.i("requestAPI", response.body().toString() + " error validate email")
                }
            }

            override fun onFailure(call: Call<UserEmailDTO>, t: Throwable) {
                Log.i("requestAPI", t.toString() + " error validate email")
            }

        })
    }

    fun validateData(name: String, email: String, password: String, phone: String, cpf: String) {
        if (_email.value == null && _password.value == null && _passwordConfirm.value == null &&
            _name.value == null && _cpf.value == null && _phone.value == null
        ) {
            register(UserModel(0, name, email, password, phone, true, cpf, ""))
        } else {
            _response.value = false
        }
    }

    private fun register(userModel: UserModel) {
        val service = RetrofitClient.createService(BuildConfig.PATH, UserService::class.java)
        userModel.password = Encryptor.encryptorString(userModel.password.toString())
        val callback = service.register(userModel)
        callback.enqueue(object : Callback<ResponseRegisterDTO> {
            override fun onResponse(
                call: Call<ResponseRegisterDTO>,
                response: Response<ResponseRegisterDTO>
            ) {
                _response.value = true
                sendingEmail(response.body()?.id, userModel.email, userModel.name)
                Log.i("requestAPI", response.body().toString() + " user")
            }

            override fun onFailure(call: Call<ResponseRegisterDTO>, t: Throwable) {
                _response.value = false
                Log.i("requestAPI", t.toString() + " error register user")

            }

        })
    }

    fun sendingEmail(id: Long?, email: String?, name: String?) {
        val service = RetrofitClient.createService(BuildConfig.PATH, EmailService::class.java)
        val callback = service.sendingEmail(
            EmailModel(
            id,
            "gogaragedev@gmail.com",
            email,
            "Bem-vindo(a) ao GoGarage - Seu novo aplicativo de garagens próximas",
            "Prezado(a) $name,\n" +
                    "\n" +
                    "Em nome de toda a equipe do GoGarage, gostaríamos de lhe dar as boas-vindas ao nosso aplicativo! É um prazer tê-lo(a) conosco como um(a) dos nossos(as) novos(as) usuários(as).\n" +
                    "\n" +
                    "O GoGarage é um aplicativo que visa facilitar a sua vida quando o assunto é encontrar uma garagem próxima de você. Com apenas alguns cliques, você poderá localizar a garagem mais próxima, verificar suas avaliações e preços, e agendar o seu serviço de forma fácil e rápida.\n" +
                    "\n" +
                    "Além disso, o GoGarage conta com uma ampla rede de garagens parceiras, que oferecem serviços de alta qualidade e excelência. Com isso, você pode ter certeza de que estará colocando o seu carro nas mãos de profissionais altamente capacitados e confiáveis.\n" +
                    "\n" +
                    "Para começar a utilizar o aplicativo, basta fazer o download em seu smartphone, criar a sua conta e começar a navegar. Não se preocupe, o processo de cadastro é simples e rápido, e em poucos minutos você estará pronto(a) para utilizar o GoGarage.\n" +
                    "\n" +
                    "Fique à vontade para explorar todas as funcionalidades do nosso aplicativo e, se precisar de ajuda, nossa equipe de suporte estará à disposição para ajudá-lo(a) em tudo o que precisar.\n" +
                    "\n" +
                    "Mais uma vez, seja bem-vindo(a) ao GoGarage. Estamos felizes por tê-lo(a) conosco e esperamos que aproveite ao máximo a nossa plataforma!\n" +
                    "\n" +
                    "Atenciosamente,\n" +
                    "Equipe GoGarage"
        )
        )

        callback.enqueue(object : Callback<EmailModel>{
            override fun onResponse(call: Call<EmailModel>, response: Response<EmailModel>) {
                Log.i("requestAPI", response.body()?.emailFrom + " respota email")

            }

            override fun onFailure(call: Call<EmailModel>, t: Throwable) {
                Log.i("requestAPI", t.toString() + " error email")

            }

        })
    }
}