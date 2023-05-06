package com.rodrigoapolo.gogarage.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.ViewModel.PerfilViewModel
import com.rodrigoapolo.gogarage.databinding.ActivityPerfilBinding

class PerfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilBinding
    private lateinit var viewModel: PerfilViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_perfil)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)
        viewModel = ViewModelProvider(this)[PerfilViewModel::class.java]
        setContentView(binding.root)

        setObserver()
        createListenerDate()
    }

    private fun setObserver() {
        TODO("Not yet implemented")
    }

    private fun createListenerDate() {
        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterGaragemActivity::class.java)
            startActivity(intent)
        }
    }


}