package com.dnd.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.trabalhocriacaopersona.utils.RacaUtil
import com.example.trabalhocriacaopersona.racas.AltoElfo
import com.example.trabalhocriacaopersona.racas.Anao
import com.example.trabalhocriacaopersona.racas.AnaoDaColina
import com.example.trabalhocriacaopersona.racas.AnaoDaMontanha
import com.example.trabalhocriacaopersona.racas.Draconato
import com.example.trabalhocriacaopersona.racas.Drow
import com.example.trabalhocriacaopersona.racas.Elfo
import com.example.trabalhocriacaopersona.racas.ElfoDaFloresta
import com.example.trabalhocriacaopersona.racas.Gnomo
import com.example.trabalhocriacaopersona.racas.GnomoDaFloresta
import com.example.trabalhocriacaopersona.racas.GnomoDasRochas
import com.example.trabalhocriacaopersona.racas.Halfling
import com.example.trabalhocriacaopersona.racas.HalflingPesLeves
import com.example.trabalhocriacaopersona.racas.HalflingRobusto
import com.example.trabalhocriacaopersona.racas.Humano
import com.example.trabalhocriacaopersona.racas.MeioElfo
import com.example.trabalhocriacaopersona.racas.MeioOrc
import com.example.trabalhocriacaopersona.racas.Tiefling

@Entity(tableName = "personagem")
data class PersonagemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    @ColumnInfo(name = "nome") val nome: String,
    @ColumnInfo(name = "raca") val raca: String,
    @ColumnInfo(name = "forca") val forca: Int,
    @ColumnInfo(name = "destreza") val destreza: Int,
    @ColumnInfo(name = "constituicao") val constituicao: Int,
    @ColumnInfo(name = "inteligencia") val inteligencia: Int,
    @ColumnInfo(name = "sabedoria") val sabedoria: Int,
    @ColumnInfo(name = "carisma") val carisma: Int
)

{
    // Função para converter PersonagemEntity em Personagem
    fun toPersonagem(): Personagem {
        return Personagem().apply {
            this.id = this@PersonagemEntity.id
            this.nome = this@PersonagemEntity.nome
            this.forca = this@PersonagemEntity.forca
            this.destreza = this@PersonagemEntity.destreza
            this.constituicao = this@PersonagemEntity.constituicao
            this.inteligencia = this@PersonagemEntity.inteligencia
            this.sabedoria = this@PersonagemEntity.sabedoria
            this.carisma = this@PersonagemEntity.carisma
            this.raca = RacaUtil.obterRacaPorNome(this@PersonagemEntity.raca) // Implementar a função `obterRacaPorNome`
        }
    }

}
