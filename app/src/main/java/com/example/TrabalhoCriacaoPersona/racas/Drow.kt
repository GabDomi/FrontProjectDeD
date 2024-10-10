package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class Drow : Raca {
    override val nome = "Drow"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.carisma += 1
    }
}