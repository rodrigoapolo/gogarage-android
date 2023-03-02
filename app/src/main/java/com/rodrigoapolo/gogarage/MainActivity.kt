package com.rodrigoapolo.gogarage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodrigoapolo.gogarage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        return setContentView(binding.root)
    }
}