package com.rodrigoapolo.gogarage.enums

enum class Situacao(val code: Int) {

    AGUARDANDO_AVALIACAO(1),
    SENDO_AVALIADO(2),
    REPROVADO(3),
    APROVADO(4),
    CANCELADO(5),
    REVISAO(6)

}