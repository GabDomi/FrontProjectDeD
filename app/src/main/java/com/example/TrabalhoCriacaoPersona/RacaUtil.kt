package com.example.trabalhocriacaopersona.utils

import com.example.trabalhocriacaopersona.racas.*
import com.dnd.model.Raca

object RacaUtil {
    fun obterRacaPorNome(nome: String): Raca? {
        return when (nome) {
            "Humano" -> Humano()
            "Elfo" -> Elfo()
            "Alto Elfo" -> AltoElfo()
            "Anão" -> Anao()
            "Halfling" -> Halfling()
            "Anão da Montanha" -> AnaoDaMontanha()
            "Draconato" -> Draconato()
            "Meio-Orc" -> MeioOrc()
            "Gnomo da Floresta" -> GnomoDaFloresta()
            "Halfling Robusto" -> HalflingRobusto()
            "Gnomo das Rochas" -> GnomoDasRochas()
            "Gnomo" -> Gnomo()
            "Tiefling" -> Tiefling()
            "Anão da Colina" -> AnaoDaColina()
            "Elfo da Floresta" -> ElfoDaFloresta()
            "Meio-Elfo" -> MeioElfo()
            "Drow" -> Drow()
            "Halfling Pés Leves" -> HalflingPesLeves()
            else -> null
        }
    }
}
