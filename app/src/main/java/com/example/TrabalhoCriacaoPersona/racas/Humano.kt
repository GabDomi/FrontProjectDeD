package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class Humano : Raca {
    override val nome = "Humano"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.forca += 1
        personagem.destreza += 1
        personagem.constituicao += 1
        personagem.inteligencia += 1
        personagem.sabedoria += 1
        personagem.carisma += 1
    }

    override fun obterBonusRacial(): Map<String, Int> {
        return mapOf(
            "Força" to 1,
            "Destreza" to 1,
            "Constituição" to 1,
            "Inteligência" to 1,
            "Sabedoria" to 1,
            "Carisma" to 1
        )
    }
}
