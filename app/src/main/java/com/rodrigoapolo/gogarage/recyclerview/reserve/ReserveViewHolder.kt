package com.rodrigoapolo.gogarage.recyclerview.reserve

import androidx.recyclerview.widget.RecyclerView
import com.rodrigoapolo.gogarage.databinding.ItemReserveBinding
import com.rodrigoapolo.gogarage.model.AgendamentoModel
import com.rodrigoapolo.gogarage.recyclerview.garage.OnItemClickListenerGarage


class ReserveViewHolder(private val itemReserveBinding: ItemReserveBinding, private val onClick: OnItemClickListenerReserve
) : RecyclerView.ViewHolder(itemReserveBinding.root){


    fun bindAgendamento(agendamento: AgendamentoModel) {
        itemReserveBinding.txtTitle.text = "Garagem do ${agendamento.garagem.pessoa.name}"
        itemReserveBinding.txtEndereco.text = "${agendamento.garagem.endereco.logradouro}, ${agendamento.garagem.endereco.numero}"
        itemReserveBinding.txtEnderecoComplement.text = "${agendamento.garagem.endereco.bairro}, ${agendamento.garagem.endereco.cidade}"
        itemReserveBinding.txtHousGarage.text = "${agendamento.dataInicio} ás ${agendamento.dataFinal}"

        itemReserveBinding.cardGarage.setOnClickListener {
            onClick.onItemClick(agendamento)
        }
    }
}