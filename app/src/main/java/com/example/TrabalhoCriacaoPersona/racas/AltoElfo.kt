package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class AltoElfo : Raca {
    override val nome = "Alto Elfo"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.inteligencia += 1
    }
}