package com.dnd.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

data class Personagem(
    var id: Long = 0L,
    var nome: String = "",
    var raca: Raca? = null,
    var forca: Int = 8,
    var destreza: Int = 8,
    var constituicao: Int = 8,
    var inteligencia: Int = 8,
    var sabedoria: Int = 8,
    var carisma: Int = 8
)  {
    // Usando estados mutáveis para que as alterações reflitam na UI
    var pontosDeVida by mutableStateOf(10 + modificador(constituicao))

    // Função que aplica os bônus raciais ao personagem
    fun aplicarBonusRacial() {
        raca?.aplicarBonusRacial(this)
        // Atualiza os pontos de vida quando constituicao muda
        pontosDeVida = 10 + modificador(constituicao)
    }

    // Função para calcular o modificador de um atributo
    private fun modificador(valor: Int): Int = (valor - 10) / 2

    // Funções para calcular os modificadores de cada atributo
    fun modificadorForca() = modificador(forca)
    fun modificadorDestreza() = modificador(destreza)
    fun modificadorConstituicao() = modificador(constituicao)
    fun modificadorInteligencia() = modificador(inteligencia)
    fun modificadorSabedoria() = modificador(sabedoria)
    fun modificadorCarisma() = modificador(carisma)

}
