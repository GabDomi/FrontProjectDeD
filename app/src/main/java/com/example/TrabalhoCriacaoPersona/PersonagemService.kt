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
import com.dnd.model.toEntity




class PersonagemService(private val distribuicaoPontosStrategy: DistribuicaoPontosStrategy, val personagemDao: PersonagemDao) {

    fun definirNome(personagem: Personagem, nome: String) {
        personagem.nome = nome
    }

    fun definirRaca(personagem: Personagem, raca: Raca) {
        personagem.raca = raca
    }

    fun distribuirPontos(personagem: Personagem, pontos: Map<String, Int>): Boolean {
        return distribuicaoPontosStrategy.distribuir(personagem, pontos)
    }

    fun getAllPersonagens(): List<PersonagemEntity> {
        return personagemDao.getAll()
    }

    fun atualizarPersonagem(personagem: Personagem) {
        val personagemEntity = personagem.toEntity()
        CoroutineScope(Dispatchers.IO).launch {
            personagemDao.update(personagemEntity)
        }
    }

    fun deletePersonagemById(id: Long) {
        CoroutineScope(Dispatchers.IO).launch {
            personagemDao.deleteById(id)
        }
    }

    fun salvarPersonagem(personagem: Personagem) {
        val personagemEntity = personagem.toEntity()

        CoroutineScope(Dispatchers.IO).launch {
            if (personagemEntity.id != 0L) {
                // Atualiza o personagem se o ID não for zero
                personagemDao.update(personagemEntity)
                Log.d("Database", "Personagem atualizado com sucesso: ${personagemEntity.nome}")
            } else {
                // Insere um novo personagem se o ID for zero
                personagemDao.insert(personagemEntity)
                Log.d("Database", "Novo personagem salvo com sucesso: ${personagemEntity.nome}")
            }
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
