package com.rodrigoapolo.gogarage.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.ViewModel.PerfilViewModel
import com.rodrigoapolo.gogarage.databinding.ActivityPerfilBinding
import com.rodrigoapolo.gogarage.model.GarageModel
import com.rodrigoapolo.gogarage.recyclerview.garage.GarageAdapter
import com.rodrigoapolo.gogarage.recyclerview.garage.OnItemClickListenerGarage
import com.rodrigoapolo.gogarage.util.SecurityPreferences

class PerfilActivity : AppCompatActivity(), OnItemClickListenerGarage {

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
        setRecyclerViewGarage()
        getRecyclerViewgarage()
    }

    private fun setObserver() {
        viewModel.setIdUser(SecurityPreferences(applicationContext).getStoredInt("id").toLong())
        viewModel.getUser()
        viewModel.msgError.observe(this){
            binding.textErro.text = it
        }
        viewModel.name.observe(this){
            binding.editName.setText(it)
        }
        viewModel.email.observe(this){
            binding.editUserEmail.setText(it)
        }
        viewModel.phone.observe(this){
            binding.editUserPhone.setText(it)
        }
    }

    private fun createListenerDate() {
        binding.imaReserve.setOnClickListener {
            val intent = Intent(this, ReserveActivity::class.java)
            startActivity(intent)
        }
        binding.imgArrow.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.editUserEmail.setOnFocusChangeListener { _, focused ->
            if (!focused) {
                viewModel.validEmail(binding.editUserEmail.text.toString(),
                    "E-mail inválido", "E-mail já cadastrado")
            }
        }
        binding.editName.setOnFocusChangeListener { v, focused ->
            if(!focused){
                viewModel.validateName(binding.editName.text.toString(),
                    binding.editUserPhone.text.toString(), "Nome inválido")
            }
        }
        binding.editUserPhone.setOnFocusChangeListener { v, focused ->
            if(!focused){
                viewModel.validatePhone(binding.editName.text.toString(),
                    binding.editUserPhone.text.toString(), "Telefone inválido")
            }
        }
        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterGaragemActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setRecyclerViewGarage() {
        viewModel.garages.observe(this){
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(applicationContext, 1)
                adapter = GarageAdapter(viewModel.garages.value!!, this@PerfilActivity)
            }
        }
    }

    private fun getRecyclerViewgarage() {
        viewModel.setGarageUser()
    }

    override fun onItemClick(garage: GarageModel) {
        createDialog(garage)
    }

    private fun createDialog(garage: GarageModel) {
        val view = layoutInflater.inflate(R.layout.delete_garage, null, false)
        view.findViewById<TextView>(R.id.text_endereco).text = "${garage.endereco.logradouro}, ${garage.endereco.numero}"
         val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        view.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            viewModel.deleteGarage(garage.id)
            viewModel.delete.observe(this){
                if(it){
                    viewModel.setGarageUser()
                    dialog.cancel()
                }
            }
        }
        view.findViewById<Button>(R.id.btn_delete).setOnClickListener {
            dialog.cancel()
        }
        dialog.show()
    }

}