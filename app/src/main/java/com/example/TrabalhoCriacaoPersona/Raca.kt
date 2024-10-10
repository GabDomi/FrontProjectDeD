package com.dnd.model

interface Raca {
    val nome: String

    fun aplicarBonusRacial(personagem: Personagem)
}