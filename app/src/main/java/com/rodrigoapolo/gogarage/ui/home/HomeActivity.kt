package com.rodrigoapolo.gogarage.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodrigoapolo.gogarage.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        return setContentView(binding.root)
    }
}