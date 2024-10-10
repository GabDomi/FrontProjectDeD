package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class AnaoDaColina : Raca {
    override val nome = "Anão da Colina"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.sabedoria += 1
    }
}