package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class Anao : Raca {
    override val nome = "Anão"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.constituicao += 2
        personagem.sabedoria += 1
    }
}