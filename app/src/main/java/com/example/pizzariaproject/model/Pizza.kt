package com.example.pizzaria.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pizza_table")
data class Pizza(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nome: String,
    val ingredientes: String,
    val preco: Double,
    val tamanho: String
)
