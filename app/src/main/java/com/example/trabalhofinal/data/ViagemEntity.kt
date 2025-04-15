package com.example.trabalhofinal.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "viagens")
data class ViagemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val destino: String,
    val tipo: String,
    val dataInicio: String,
    val dataFim: String,
    val orcamento: String
)
