package com.dnd.service

import android.util.Log
import com.dnd.model.Personagem
import com.dnd.model.Raca
import com.dnd.strategy.DistribuicaoPontosStrategy
import com.dnd.model.AppDatabase
import com.dnd.model.PersonagemDao
import com.dnd.model.PersonagemEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class PersonagemService(private val distribuicaoPontosStrategy: DistribuicaoPontosStrategy, private val personagemDao: PersonagemDao) {

    fun definirNome(personagem: Personagem, nome: String) {
        personagem.nome = nome
    }

    fun definirRaca(personagem: Personagem, raca: Raca) {
        personagem.raca = raca
    }

    fun distribuirPontos(personagem: Personagem, pontos: Map<String, Int>): Boolean {
        return distribuicaoPontosStrategy.distribuir(personagem, pontos)
    }

    fun salvarPersonagem(personagem: Personagem) {
        val personagemEntity = PersonagemEntity(
            id = personagem.id,
            nome = personagem.nome,
            raca = personagem.raca?.nome ?: "",
            forca = personagem.forca,
            destreza = personagem.destreza,
            constituicao = personagem.constituicao,
            inteligencia = personagem.inteligencia,
            sabedoria = personagem.sabedoria,
            carisma = personagem.carisma
        )
        try {
            Log.d("Database", "Salvando personagem: ${personagemEntity.nome}")
            personagemDao.insert(personagemEntity)
            Log.d("Database", "Personagem salvo com sucesso: ${personagemEntity.nome}")
        } catch (e: Exception) {
            Log.e("Database", "Erro ao salvar personagem: ${e.message}")
            e.printStackTrace()
        }
    }

    suspend fun carregarPersonagem(id: Long): Personagem? {
        val personagemEntity = personagemDao.getPersonagemById(id)
        return personagemEntity?.toPersonagem()
    }

    fun calcularCusto(pontos: Map<String, Int>): Int {
        var totalCusto = 0
        val custoPorValor = mapOf(
            8 to 0, 9 to 1, 10 to 2, 11 to 3,
            12 to 4, 13 to 5, 14 to 7, 15 to 9
        )

        for ((atributo, valor) in pontos) {
            totalCusto += custoPorValor[valor] ?: 0
        }
        return totalCusto
    }


}
