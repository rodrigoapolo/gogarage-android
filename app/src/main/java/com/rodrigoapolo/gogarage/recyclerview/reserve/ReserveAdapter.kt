package com.rodrigoapolo.gogarage.recyclerview.reserve

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoapolo.gogarage.databinding.ItemReserveBinding
import com.rodrigoapolo.gogarage.model.AgendamentoModel
import com.rodrigoapolo.gogarage.recyclerview.garage.OnItemClickListenerGarage


class ReserveAdapter(private val agendamento: List<AgendamentoModel>, private val onClickListener: OnItemClickListenerReserve) :
    RecyclerView.Adapter<ReserveViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReserveViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemReserveBinding.inflate(from, parent, false)

        return ReserveViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: ReserveViewHolder, position: Int) {
        holder.bindAgendamento(agendamento[position])
    }

    override fun getItemCount(): Int = agendamento.size

}

