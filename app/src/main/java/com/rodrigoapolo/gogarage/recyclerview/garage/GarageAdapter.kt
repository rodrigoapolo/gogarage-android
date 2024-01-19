package com.rodrigoapolo.gogarage.recyclerview.garage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoapolo.gogarage.databinding.ItemGarageBinding
import com.rodrigoapolo.gogarage.model.GarageModel


class GarageAdapter(private val garageModel: List<GarageModel>, private val onClickListener: OnItemClickListenerGarage) :
    RecyclerView.Adapter<GarageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GarageViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemGarageBinding.inflate(from, parent, false)

        return GarageViewHolder(binding, onClickListener)
    }

    override fun onBindViewHolder(holder: GarageViewHolder, position: Int) {
        holder.bindGarage(garageModel[position])
    }

    override fun getItemCount(): Int = garageModel.size

}

