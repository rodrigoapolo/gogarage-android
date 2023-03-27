package com.rodrigoapolo.gogarage.ui.home.recyclerview

import androidx.recyclerview.widget.RecyclerView
import com.rodrigoapolo.gogarage.databinding.ItemGarageBinding
import com.rodrigoapolo.gogarage.model.GarageModel


class ItemViewHolder(
    private val itemGarageBinding: ItemGarageBinding
) : RecyclerView.ViewHolder(itemGarageBinding.root) {

    fun bindGarage(garageModel: GarageModel) {
        itemGarageBinding.txtTitle.text = "Garagem do ${garageModel.pessoa.name}"
        itemGarageBinding.txtEndereco.text = "${garageModel.endereco.logradouro}, ${garageModel.endereco.numero}"
        itemGarageBinding.txtEnderecoComplement.text = "${garageModel.endereco.bairro}, ${garageModel.endereco.cidade}"
    }
}