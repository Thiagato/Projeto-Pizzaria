package com.example.pizzaria.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cliente_table")
data class Cliente(
    @PrimaryKey(autoGenerate = true) val clienteId: Int = 0,
    val nome: String,
    val telefone: String,
    val endereco: String
)