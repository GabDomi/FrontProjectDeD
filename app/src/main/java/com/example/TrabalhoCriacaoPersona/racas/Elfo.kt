package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class Elfo : Raca {
    override val nome = "Elfo"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.destreza += 2
        personagem.inteligencia += 1
    }

}