package com.example.trabalhocriacaopersona.racas

import com.dnd.model.Personagem
import com.dnd.model.Raca

class ElfoDaFloresta : Raca {
    override val nome = "Elfo da Floresta"

    override fun aplicarBonusRacial(personagem: Personagem) {
        personagem.sabedoria += 1
    }

    override fun obterBonusRacial(): Map<String, Int> {
        return mapOf("Sabedoria" to 1)
    }
}