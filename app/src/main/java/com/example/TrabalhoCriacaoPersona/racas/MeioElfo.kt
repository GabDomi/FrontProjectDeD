package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class MeioElfo : Raca {
    override val nome = "Meio-elfo"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.carisma += 2
    }
}