package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class AnaoDaMontanha : Raca {
    override val nome = "Anão da Montanha"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.forca += 2
    }

    override fun obterBonusRacial(): Map<String, Int> {
        return mapOf("Força" to 2)
    }
}