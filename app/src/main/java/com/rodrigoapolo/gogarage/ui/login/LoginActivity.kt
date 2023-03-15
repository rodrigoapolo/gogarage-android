package com.rodrigoapolo.gogarage.ui.login

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.databinding.ActivityLoginBinding
import com.rodrigoapolo.gogarage.ui.home.HomeActivity
import com.rodrigoapolo.gogarage.ui.registerUser.RegisterUserActivity
import java.util.*
import kotlin.concurrent.timerTask

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: LoginViewModel
    private lateinit var village: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)
        createListenerData()
        setObserver()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        return setContentView(binding.root)
    }

    private fun setObserver() {
        viewModel.email().observe(this) {
            binding.emailContainer.helperText = it
        }

        viewModel.password().observe(this) {
            binding.passwordContainer.helperText = it
        }

        viewModel.response().observe(this) {
            getLocationUser()
            binding.progressBar2.visibility = View.VISIBLE
            Timer().schedule(timerTask {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                intent.putExtra("village", village)
                startActivity(intent)
            }, 3000)
        }
    }

    private fun createListenerData() {
        binding.editEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.validEmail(binding.editEmail)
            }
        }

        binding.editPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.validPassword(binding.editPassword)
            }
        }

        binding.buttonEnter.setOnClickListener {
            hideSoftKeyBoard()
            viewModel.validEmail(binding.editEmail)
            viewModel.validPassword(binding.editPassword)
            viewModel.doLogin(binding.editEmail, binding.editPassword)
        }

        binding.textRegister.setOnClickListener {
            val intent = Intent(this, RegisterUserActivity::class.java)
            intent.putExtra("id", viewModel.response().value)
            startActivity(intent)
        }
    }

    private fun getLocationUser() {
        val task = fusedLocationClient.lastLocation

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                101
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                val geocoder = Geocoder(this, Locale.getDefault())
                val addressesList = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                village = formatNeighborhood(addressesList.toString())
                Log.i("village", village)
            }
        }
    }

    private fun formatNeighborhood(address: String): String {
        return address.substringAfter("- ").substringBefore(",")
    }

    private fun hideSoftKeyBoard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if (imm.isAcceptingText) {
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

}