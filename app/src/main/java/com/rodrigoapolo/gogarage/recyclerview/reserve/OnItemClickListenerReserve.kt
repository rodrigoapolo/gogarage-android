package com.rodrigoapolo.gogarage.recyclerview.reserve

import com.rodrigoapolo.gogarage.model.AgendamentoModel
import com.rodrigoapolo.gogarage.model.GarageModel

interface OnItemClickListenerReserve {
    fun onItemClick(agendamento: AgendamentoModel)
}