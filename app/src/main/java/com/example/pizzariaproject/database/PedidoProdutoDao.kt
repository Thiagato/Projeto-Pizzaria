package com.example.pizzaria.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pizzaria.model.PedidoProduto

@Dao
interface PedidoProdutoDao {
    @Insert
    suspend fun insertPedidoProduto(pedidoProduto: PedidoProduto)

    @Query("SELECT * FROM pedido_produto_table WHERE pedidoId = :pedidoId")
    suspend fun getProdutosPorPedido(pedidoId: Int): List<PedidoProduto>
}
