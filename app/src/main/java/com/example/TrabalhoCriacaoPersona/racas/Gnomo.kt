package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class Gnomo : Raca {
    override val nome = "Gnomo"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.inteligencia += 2
    }
}