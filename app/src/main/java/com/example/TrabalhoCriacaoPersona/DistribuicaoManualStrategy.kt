package com.dnd.strategy
import com.dnd.model.Personagem

interface DistribuicaoPontosStrategy {
    fun distribuir(personagem: Personagem, pontos: Map<String, Int>): Boolean
}

class DistribuicaoManualStrategy : DistribuicaoPontosStrategy {
    private val custoPorValor = mapOf(
        8 to 0, 9 to 1, 10 to 2, 11 to 3,
        12 to 4, 13 to 5, 14 to 7, 15 to 9
    )

    override fun distribuir(personagem: Personagem, pontos: Map<String, Int>): Boolean {
        var totalCusto = 0
        for ((atributo, valor) in pontos) {
            if (valor < 8 || valor > 15) return false // Validação de faixa de valores
            totalCusto += custoPorValor[valor] ?: 0
        }

        // Verifica se o custo total não ultrapassa os 27 pontos
        if (totalCusto > 27) return false

        // Aplicando os pontos ao personagem
        personagem.forca = pontos["forca"] ?: 8
        personagem.destreza = pontos["destreza"] ?: 8
        personagem.constituicao = pontos["constituicao"] ?: 8
        personagem.inteligencia = pontos["inteligencia"] ?: 8
        personagem.sabedoria = pontos["sabedoria"] ?: 8
        personagem.carisma = pontos["carisma"] ?: 8
        return true
    }
}
