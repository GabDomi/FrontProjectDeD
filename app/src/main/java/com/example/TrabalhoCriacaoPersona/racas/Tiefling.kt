package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class Tiefling : Raca {
    override val nome = "Tiefling"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.inteligencia += 1
        personagem.carisma += 2
    }

    override fun obterBonusRacial(): Map<String, Int> {
        return mapOf(
            "Inteligência" to 1,
            "Carisma" to 2
        )
    }
}