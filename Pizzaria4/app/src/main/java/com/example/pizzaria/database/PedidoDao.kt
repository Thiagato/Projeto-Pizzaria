package com.example.pizzaria.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pizzaria.model.Pedido

@Dao
interface PedidoDao {
    @Insert
    suspend fun inserir(pedido: Pedido)

    @Query("SELECT * FROM pedido_table")
    suspend fun obterTodosPedidos(): List<Pedido>

    @Query("SELECT * FROM pedido_table WHERE pedidoId = :id")
    suspend fun obterPedidoPorId(id: Int): Pedido
}
