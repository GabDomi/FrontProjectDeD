package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class Halfling : Raca {
    override val nome = "Halfling"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.destreza += 1
    }
}