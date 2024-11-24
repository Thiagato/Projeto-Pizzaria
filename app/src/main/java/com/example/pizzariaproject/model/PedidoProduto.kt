package com.example.pizzaria.model

import androidx.room.Entity

@Entity(tableName = "pedido_produto_table", primaryKeys = ["pedidoId", "produtoId"])
data class PedidoProduto(
    val pedidoId: Int,
    val produtoId: Int,
    val produtoNome: String,
    val quantidade: Int
)
