package com.rodrigoapolo.gogarage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rodrigoapolo.gogarage.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        val extras = intent.extras

        if (extras != null) {
            binding.textView.text = extras.getString("id")
        }

        return setContentView(binding.root)
    }
}