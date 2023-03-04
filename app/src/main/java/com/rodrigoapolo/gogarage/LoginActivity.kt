package com.rodrigoapolo.gogarage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodrigoapolo.gogarage.api.Endpoint
import com.rodrigoapolo.gogarage.databinding.ActivityLoginBinding
import com.rodrigoapolo.gogarage.model.User
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

        return setContentView(binding.root)
    }

    fun login() {
        val retrofitClient = NetworkUtils.getRetrofitInstance("UrlBase")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        val callback = endpoint.authenticate(
            binding.editEmail.text.toString(),
            binding.editPassword.text.toString()
        )

        callback.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {

            }

            override fun onFailure(call: Call<User>, t: Throwable) {

            }
        })
    }
}