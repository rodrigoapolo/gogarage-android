package com.rodrigoapolo.gogarage.ui.home.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.rodrigoapolo.gogarage.databinding.ItemGarageBinding
import com.rodrigoapolo.gogarage.model.garage.Garage

class ItemViewHolder(
    private val itemGarageBinding: ItemGarageBinding
) : RecyclerView.ViewHolder(itemGarageBinding.root) {

    fun bindGarage(garage: Garage) {
        itemGarageBinding.txtTitle.text = "Garagem do ${garage.pessoa.name }"
    }
}