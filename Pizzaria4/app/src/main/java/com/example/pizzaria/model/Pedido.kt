package com.example.pizzaria.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pedido_table")
data class Pedido(
    @PrimaryKey(autoGenerate = true) val pedidoId: Int = 0,
    val clienteId: Int,
    val data: String,
    val valorTotal: Double
)