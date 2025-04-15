package com.example.trabalhofinal.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ViagemDao {

    @Insert
    suspend fun insertViagem(viagem: ViagemEntity)

    @Update
    suspend fun updateViagem(viagem: ViagemEntity)

    @Delete
    suspend fun deleteViagem(viagem: ViagemEntity)

    @Query("SELECT * FROM viagens")
    suspend fun getAllViagens(): List<ViagemEntity>

    @Query("SELECT * FROM viagens WHERE id = :id")
    suspend fun getViagemById(id: Int): ViagemEntity?
}
