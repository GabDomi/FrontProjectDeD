package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class HalflingPesLeves : Raca {
    override val nome = "Halfling pés-leves"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.carisma += 1
    }

    override fun obterBonusRacial(): Map<String, Int> {
        return mapOf("Carisma" to 1)
    }
}