package com.rodrigoapolo.gogarage.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoapolo.gogarage.View.ShowGarageActivity
import com.rodrigoapolo.gogarage.databinding.ItemGarageBinding
import com.rodrigoapolo.gogarage.dto.GarageDTO
import com.rodrigoapolo.gogarage.model.GarageModel


class GarageAdapter(private val garageModel: List<GarageModel>) :
    RecyclerView.Adapter<GarageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GarageViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemGarageBinding.inflate(from, parent, false)

        return GarageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GarageViewHolder, position: Int) {
        holder.bindGarage(garageModel[position])
        holder.itemView.setOnClickListener{
            val garage: GarageDTO = GarageDTO(
                garageModel[position].id,
                garageModel[position].cobertura,
                garageModel[position].horarioInicio,
                garageModel[position].horarioTermino,
                garageModel[position].taxaHorario,
                garageModel[position].valorHora,
                garageModel[position].alturaVaga,
                garageModel[position].larguraVaga,
                "",
                "",
                garageModel[position].endereco,
                garageModel[position].pessoa.id
            )
            val intent = Intent(it.context, ShowGarageActivity::class.java)
            val putExtra = intent.putExtra("garage", garage)
            it.context.startActivities(arrayOf(intent))
        }
    }

    override fun getItemCount(): Int = garageModel.size

}

