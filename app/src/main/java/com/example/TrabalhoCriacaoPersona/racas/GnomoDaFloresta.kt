package com.example.trabalhocriacaopersona.racas


import com.dnd.model.Personagem
import com.dnd.model.Raca

class GnomoDaFloresta : Raca {
    override val nome = "Gnomo da floresta"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.destreza += 1
    }

    override fun obterBonusRacial(): Map<String, Int> {
        return mapOf("Destreza" to 1)
    }
}