package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca
import java.io.Serializable

class AnaoDaMontanha : Raca {
    override val nome = "Anão da Montanha"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.forca += 2
    }
}