package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class Draconato : Raca {
    override val nome = "Draconato"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.forca += 2
        personagem.carisma += 1
    }
}