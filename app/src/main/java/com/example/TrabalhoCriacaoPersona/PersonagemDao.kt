package com.dnd.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PersonagemDao {
    @Query("SELECT * FROM personagem")
    fun getAll(): List<PersonagemEntity>

    @Query("SELECT * FROM personagem WHERE id = :id")
    suspend fun getPersonagemById(id: Long): PersonagemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(personagem: PersonagemEntity)

    @Update
    suspend fun update(personagem: PersonagemEntity)

    @Query("DELETE FROM personagem WHERE id = :id")
    suspend fun deleteById(id: Long)
}
