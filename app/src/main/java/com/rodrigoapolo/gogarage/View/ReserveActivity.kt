package com.rodrigoapolo.gogarage.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rodrigoapolo.gogarage.R
import com.rodrigoapolo.gogarage.ViewModel.ReserveViewModel
import com.rodrigoapolo.gogarage.databinding.ActivityReserveBinding
import com.rodrigoapolo.gogarage.model.AgendamentoModel
import com.rodrigoapolo.gogarage.recyclerview.reserve.OnItemClickListenerReserve
import com.rodrigoapolo.gogarage.recyclerview.reserve.ReserveAdapter
import com.rodrigoapolo.gogarage.util.SecurityPreferences

class ReserveActivity : AppCompatActivity(), OnItemClickListenerReserve {
    private lateinit var viewModel: ReserveViewModel
    private lateinit var binding: ActivityReserveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReserveBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this)[ReserveViewModel::class.java]
        setContentView(binding.root)

        setRecyclerViewGarage()
        getRecyclerViewgarage()
        createListenerData()
        setObserver()
    }

    private fun createListenerData() {
        viewModel.getReserva()
        binding.imgArrow.setOnClickListener {
            val intent = Intent(this, PerfilActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setObserver() {
        //TODO
    }

    private fun getRecyclerViewgarage() {
        viewModel.setIdUser(SecurityPreferences(applicationContext).getStoredInt("id").toLong())

    }

    private fun setRecyclerViewGarage() {
        viewModel.agendamentos.observe(this){
            binding.recyclerView.apply {
                layoutManager = GridLayoutManager(applicationContext, 1)
                adapter = ReserveAdapter(viewModel.agendamentos.value!!, this@ReserveActivity)
            }
        }
    }

    override fun onItemClick(agendamento: AgendamentoModel) {
        createDialog(agendamento)
    }

    private fun createDialog(agendamento: AgendamentoModel) {
        val view = layoutInflater.inflate(R.layout.delete_reserva, null, false)
        view.findViewById<TextView>(R.id.text_endereco).text = "${agendamento.garagem.endereco.logradouro}, ${agendamento.garagem.endereco.numero}"
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(view)
        view.findViewById<Button>(R.id.btn_confirm).setOnClickListener {
            viewModel.deleteAgendamento(agendamento.id)
            viewModel.delete.observe(this){
                if(it){
                    viewModel.getReserva()
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

