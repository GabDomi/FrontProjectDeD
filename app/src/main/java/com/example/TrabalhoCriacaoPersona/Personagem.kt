package com.dnd.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.math.floor
import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Personagem(
    var id: Long = 0L,
    var nome: String = "",
    var raca: @RawValue Raca? = null, // Adicione @RawValue aqui
    var forca: Int = 8,
    var destreza: Int = 8,
    var constituicao: Int = 8,
    var inteligencia: Int = 8,
    var sabedoria: Int = 8,
    var carisma: Int = 8
) : Parcelable {
    // Usando estados mutáveis para que as alterações reflitam na UI
    var pontosDeVida by mutableStateOf(10 + modificador(constituicao))

    // Função que aplica os bônus raciais ao personagem
    fun aplicarBonusRacial() {
        raca?.aplicarBonusRacial(this)
        // Atualiza os pontos de vida quando constituicao muda
        pontosDeVida = 10 + modificador(constituicao)
    }

    // Função para calcular o modificador de um atributo
    private fun modificador(valor: Int):  Int = floor((valor - 10) / 2.0).toInt()

    // Funções para calcular os modificadores de cada atributo
    fun modificadorForca() = modificador(forca)
    fun modificadorDestreza() = modificador(destreza)
    fun modificadorConstituicao() = modificador(constituicao)
    fun modificadorInteligencia() = modificador(inteligencia)
    fun modificadorSabedoria() = modificador(sabedoria)
    fun modificadorCarisma() = modificador(carisma)

}

// Método de extensão para converter Personagem em PersonagemEntity
fun Personagem.toEntity(): PersonagemEntity {
    return PersonagemEntity(
        id = this.id,
        nome = this.nome,
        raca = this.raca?.nome ?: "",
        forca = this.forca,
        destreza = this.destreza,
        constituicao = this.constituicao,
        inteligencia = this.inteligencia,
        sabedoria = this.sabedoria,
        carisma = this.carisma
    )
}
