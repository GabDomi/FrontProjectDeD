package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class MeioOrc : Raca {
    override val nome = "Meio-Orc"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.constituicao += 1
        personagem.forca += 2
    }
}