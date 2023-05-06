package com.rodrigoapolo.gogarage.recyclerview

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.rodrigoapolo.gogarage.View.RegisterUserActivity
import com.rodrigoapolo.gogarage.View.ShowGarageActivity
import com.rodrigoapolo.gogarage.databinding.ItemGarageBinding
import com.rodrigoapolo.gogarage.dto.GarageDTO
import com.rodrigoapolo.gogarage.model.GarageModel


class GarageViewHolder(private val itemGarageBinding: ItemGarageBinding, private val onClick: OnItemClickListener
) : RecyclerView.ViewHolder(itemGarageBinding.root){


    fun bindGarage(garageModel: GarageModel) {
        itemGarageBinding.txtTitle.text = "Garagem do ${garageModel.pessoa.name}"
        itemGarageBinding.txtEndereco.text = "${garageModel.endereco.logradouro}, ${garageModel.endereco.numero}"
        itemGarageBinding.txtEnderecoComplement.text = "${garageModel.endereco.bairro}, ${garageModel.endereco.cidade}"

        itemGarageBinding.cardGarage.setOnClickListener {
            onClick.onItemClick(garageModel)
        }
    }
}