package com.rodrigoapolo.gogarage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rodrigoapolo.gogarage.api.Endpoint
import com.rodrigoapolo.gogarage.databinding.ActivityLoginBinding
import com.rodrigoapolo.gogarage.model.User
import com.rodrigoapolo.gogarage.model.UserLogin
import com.rodrigoapolo.gogarage.util.NetworkUtils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.textRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.buttonEnter.setOnClickListener {
            login()
        }

        return setContentView(binding.root)
    }

    private fun login() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("http://192.168.1.12:8080")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        val userLogin = UserLogin(
            binding.editEmail.text.toString(),
            binding.editPassword.text.toString()
        )

        val callback = endpoint.authenticate(
            userLogin
        )
        Log.i("requestAPI", userLogin.toString())
        callback.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                Log.i("requestAPI", response.body().toString() + "sucesso")
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                Log.i("requestAPI", t.toString() + "error")
            }
        })
    }
}