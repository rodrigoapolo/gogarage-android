package com.rodrigoapolo.gogarage.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import com.rodrigoapolo.gogarage.databinding.ActivityMainBinding
import com.rodrigoapolo.gogarage.R

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        window.statusBarColor = ContextCompat.getColor(this, R.color.white)

        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }, 2000)

        return setContentView(binding.root)
    }
}