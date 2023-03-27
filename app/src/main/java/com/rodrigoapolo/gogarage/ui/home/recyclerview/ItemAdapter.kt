package com.rodrigoapolo.gogarage.ui.home.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoapolo.gogarage.databinding.ItemGarageBinding
import com.rodrigoapolo.gogarage.model.GarageModel


class ItemAdapter(private val garageModel: List<GarageModel>) : RecyclerView.Adapter<ItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val from = LayoutInflater.from(parent.context)
        val binding = ItemGarageBinding.inflate(from, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindGarage(garageModel[position])
    }

    override fun getItemCount(): Int = garageModel.size

}