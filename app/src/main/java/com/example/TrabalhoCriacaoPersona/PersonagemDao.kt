package com.dnd.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
//@RewriteQueriesToDropUnusedColumns
interface PersonagemDao {
    @Query("SELECT * FROM personagem")
    fun getAll(): List<PersonagemEntity>

    @Query("SELECT * FROM personagem WHERE id = :id")
    suspend fun getPersonagemById(id: Long): PersonagemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(personagem: PersonagemEntity)

    @Query("DELETE FROM personagem WHERE id = :id")
    fun deleteById(id: Long)
}
