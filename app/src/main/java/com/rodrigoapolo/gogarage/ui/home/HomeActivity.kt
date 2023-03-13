package com.rodrigoapolo.gogarage.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.rodrigoapolo.gogarage.databinding.ActivityHomeBinding
import com.rodrigoapolo.gogarage.model.User
import com.rodrigoapolo.gogarage.model.garage.Address
import com.rodrigoapolo.gogarage.model.garage.Garage
import com.rodrigoapolo.gogarage.ui.home.recyclerview.ItemAdapter

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var listGarage: MutableList<Garage>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        listGarage = mutableListOf()
        populate()

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(applicationContext, 1)
            adapter = ItemAdapter(listGarage)
        }

        return setContentView(binding.root)
    }

    private fun populate() {
        val garage1 = Garage(
            0,
            true,
            "http//teste",
            "8:00",
            "20:00",
            12.0,
            30.0,
            12.3,
            8.2,
            disponibilidade = false,
            status = true,
            situacao = 1,
            latitude = "0.0000",
            longitude = "1.0000",
            endereco = Address(
                "Rua Penápolis, 394",
                "08564-200",
                "",
                "Vila Coqueral",
                "Poá",
                "SP",
                "394"
            ),
            pessoa = User(
                id = 0,
                name = "Gabriel",
                email = "gabriel.contato2013@outlook.com",
                password = "123456",
                phone = "11977527475",
                status = true,
                cpf = "12345678912",
                cnpj = ""
            )
        )

        listGarage.add(garage1)
        listGarage.add(garage1)
        listGarage.add(garage1)
        listGarage.add(garage1)
        listGarage.add(garage1)

    }
}