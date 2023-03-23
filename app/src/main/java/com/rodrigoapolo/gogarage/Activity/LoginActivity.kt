package com.rodrigoapolo.gogarage.Activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.ViewModel.LoginViewModel
import com.rodrigoapolo.gogarage.databinding.ActivityLoginBinding
import com.rodrigoapolo.gogarage.util.SecurityPreferences
import java.util.*
import kotlin.concurrent.timerTask

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_500)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (!isGPSPremissionGranted()) {
            requestGPSPremission()
        }

        createListenerData()
        setObserver()


        return setContentView(binding.root)
    }

    private fun requestGPSPremission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            GPS_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GPS_PERMISSION_CODE) {
            if (grantResults.firstOrNull() != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(
                    this,
                    "Por favor aceite a permissão de localização.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun isGPSPremissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
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
            SecurityPreferences(applicationContext).storeInt(
                "id",
                (viewModel.response().value ?: 0).toInt()
            )

        }

        viewModel.village().observe(this) {
            SecurityPreferences(applicationContext).storeString(
                "village",
                viewModel.village().value.toString()
            )
            Timer().schedule(timerTask {
                val intent = Intent(applicationContext, HomeActivity::class.java)
                startActivity(intent)
            }, 2000)
        }
    }

    private fun createListenerData() {
        binding.editEmail.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.validEmail(binding.editEmail.text.toString(), "E-mail inválido")
            }
        }

        binding.editPassword.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.validPassword(binding.editPassword.text.toString(), "Senha inválida")
            }
        }

        binding.buttonEnter.setOnClickListener {
            if (!isGPSPremissionGranted()) {
                requestGPSPremission()
            } else {
                hideSoftKeyBoard()
                viewModel.validEmail(binding.editEmail.text.toString(), "E-mail inválido")
                viewModel.validPassword(binding.editPassword.text.toString(), "Senha inválida")
                viewModel.doLogin(
                    binding.editEmail.text.toString(),
                    binding.editPassword.text.toString(),
                    "Email ou Senha inválido"
                )
            }
        }

        binding.textRegister.setOnClickListener {
            val intent = Intent(this, RegisterUserActivity::class.java)
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
                GPS_PERMISSION_CODE
            )
            return
        }
        task.addOnSuccessListener {
            if (it != null) {
                val geocoder = Geocoder(this, Locale.getDefault())
                val addressesList = geocoder.getFromLocation(it.latitude, it.longitude, 1)
                viewModel.setVillage(formatNeighborhood(addressesList.toString()))
                Log.i("village", "HOME"+ viewModel.village().toString())
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

    companion object {
        private const val GPS_PERMISSION_CODE = 201
    }

}