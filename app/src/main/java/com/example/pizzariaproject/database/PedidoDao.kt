package com.example.pizzaria.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pizzaria.model.Pedido

@Dao
interface PedidoDao {
    @Insert
    suspend fun insertPedido(pedido: Pedido): Long // Retorna o ID do pedido inserido

    @Query("SELECT * FROM pedido_table WHERE pedidoId = :pedidoId")
    suspend fun getPedidoById(pedidoId: Int): Pedido?
}
