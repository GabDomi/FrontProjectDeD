package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class HalflingRobusto : Raca {
    override val nome = "Halfling Robusto"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.constituicao += 1
    }

    override fun obterBonusRacial(): Map<String, Int> {
        return mapOf("Constituição" to 1)
    }
}